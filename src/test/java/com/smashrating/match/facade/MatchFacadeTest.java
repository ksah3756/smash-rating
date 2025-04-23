package com.smashrating.match.facade;

import com.smashrating.auth.TestSecurityConfig;
import com.smashrating.common.annotation.IntegrationTest;
import com.smashrating.match.MatchResultTestFactory;
import com.smashrating.match.application.PendingMatchValidateService;
import com.smashrating.match.application.command.PendingMatchCommandService;
import com.smashrating.match.application.query.MatchResultQueryService;
import com.smashrating.match.application.query.PendingMatchQueryService;
import com.smashrating.match.domain.MatchResult;
import com.smashrating.match.domain.PendingMatch;
import com.smashrating.match.dto.MatchResultRequest;
import com.smashrating.match.dto.MatchResultResponse;
import com.smashrating.match.dto.PendingMatchCreateRequest;
import com.smashrating.match.dto.PendingMatchResponse;
import com.smashrating.match.enums.PendingMatchStatus;
import com.smashrating.match.exception.MatchErrorCode;
import com.smashrating.match.exception.MatchException;
import com.smashrating.match.infrastructure.MatchResultRepository;
import com.smashrating.match.infrastructure.PendingMatchRepository;
import com.smashrating.user.UserTestFactory;
import com.smashrating.user.application.query.UserQueryService;
import com.smashrating.user.domain.Role;
import com.smashrating.user.domain.User;
import com.smashrating.user.exception.UserErrorCode;
import com.smashrating.user.exception.UserException;
import com.smashrating.user.infrastructure.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@IntegrationTest
class MatchFacadeTest {

    @Autowired
    private MatchFacade matchFacade;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PendingMatchRepository pendingMatchRepository;

    @Autowired
    private MatchResultRepository matchResultRepository;

    private User sender;
    private User receiver;
    private User me;
    private User opponent;
    private PendingMatch pendingMatch;

    @BeforeEach
    void setUp() {
        User user1 = UserTestFactory.createUser1();
        User user2 = UserTestFactory.createUser2();

        sender = userRepository.save(user1);
        receiver = userRepository.save(user2);
        me = sender;
        opponent = receiver;

        pendingMatch = PendingMatch.create(sender.getId(), receiver.getId());
    }

    @Test
    @DisplayName("받은 매치 목록을 조회할 수 있다")
    void getReceivedMatches() {
        // given
        pendingMatchRepository.save(pendingMatch);

        // when
        List<PendingMatchResponse> responses = matchFacade.getReceivedMatches(receiver.getId());

        // then
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).id()).isEqualTo(pendingMatch.getId());
        assertThat(responses.get(0).opponentUsername()).isEqualTo(sender.getUsername());
    }

    @Test
    @DisplayName("보낸 매치 목록을 조회할 수 있다")
    void getSentMatches() {
        // given
        pendingMatchRepository.save(pendingMatch);

        // when
        List<PendingMatchResponse> responses = matchFacade.getSentMatches(sender.getId());

        // then
        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).id()).isEqualTo(pendingMatch.getId());
        assertThat(responses.get(0).opponentUsername()).isEqualTo(receiver.getUsername());
    }

    @Test
    @DisplayName("마지막 조회 ID 이전의 매치 결과를 최신순(pk 역순)으로 size 만큼 조회할 수 있다")
    void getMatchHistory() {
        // given
        List<MatchResult> matchResults = MatchResultTestFactory.createMatchResults(me.getId(), opponent.getId(), 20);
        matchResultRepository.saveAll(matchResults);

        // when
        Long lastMatchResultId = 11L;
        int size = 10;
        MatchResultRequest request = new MatchResultRequest(lastMatchResultId, size);
        List<MatchResultResponse> matchHistory = matchFacade.getMatchHistory(sender.getId(), request);

        // then
        assertThat(matchHistory).hasSize(size);
        IntStream.range(0, matchHistory.size()).forEach(i -> {
            MatchResultResponse matchResultResponse = matchHistory.get(i);
            assertEquals(matchResultResponse.id(), lastMatchResultId-1-i);
            assertEquals(opponent.getUsername(), matchResultResponse.opponentUsername());
            assertEquals(opponent.getNickname(), matchResultResponse.opponentNickname());
            assertEquals(15, matchResultResponse.myGameScore());
            assertEquals(10, matchResultResponse.opponentGameScore());
            assertEquals(32.0, matchResultResponse.updatedRatingScore());
        });

    }

    @Test
    @DisplayName("매치 요청을 생성할 수 있다")
    void createPendingMatch() {
        // given
        PendingMatchCreateRequest request = new PendingMatchCreateRequest(receiver.getUsername());

        // when
        matchFacade.createPendingMatch(sender.getId(), request);

        // then
        List<PendingMatchResponse> receivedPendingMatch = pendingMatchRepository.getReceivedPendingMatch(receiver.getId());
        assertThat(receivedPendingMatch).hasSize(1);
    }

    @Test
    @DisplayName("존재하지 않는 사용자에게 매치 요청을 보내면 예외가 발생한다")
    void createPendingMatchWithNonExistentUser() {
        // given
        PendingMatchCreateRequest request = new PendingMatchCreateRequest("nonExistentUser");

        // when & then
        UserException userException = assertThrows(UserException.class, () ->
                matchFacade.createPendingMatch(sender.getId(), request));
        assertThat(userException.getErrorCode()).isEqualTo(UserErrorCode.USER_NOT_FOUND);
    }

    @Test
    @DisplayName("매치를 수락할 수 있다")
    void acceptMatch() {
        // given
        pendingMatchRepository.save(pendingMatch);

        // when
        matchFacade.acceptMatch(receiver.getId(), pendingMatch.getId());

        // then
        PendingMatch updatedMatch = pendingMatchRepository.findById(pendingMatch.getId()).orElseThrow();
        assertThat(updatedMatch.getStatus()).isEqualTo(PendingMatchStatus.ACCEPTED);
    }

    @Test
    @DisplayName("매치를 거절할 수 있다")
    void rejectMatch() {
        // given
        pendingMatchRepository.save(pendingMatch);

        // when
        matchFacade.rejectMatch(receiver.getId(), pendingMatch.getId());

        // then
        PendingMatch updatedMatch = pendingMatchRepository.findById(pendingMatch.getId()).orElseThrow();
        assertThat(updatedMatch.getStatus()).isEqualTo(PendingMatchStatus.REJECTED);
    }

    @Test
    @DisplayName("존재하지 않는 매치를 수락하려면 예외가 발생한다")
    void acceptNonExistentMatch() {
        // when & then
        MatchException matchException = assertThrows(MatchException.class, () ->
                matchFacade.acceptMatch(receiver.getId(), 999L));
        assertThat(matchException.getErrorCode()).isEqualTo(MatchErrorCode.MATCH_NOT_FOUND);
    }

    @Test
    @DisplayName("매치 수신자가 아닌 사용자가 매치를 수락하려면 예외가 발생한다")
    void acceptMatchByNonReceiver() {
        //given
        pendingMatchRepository.save(pendingMatch);
        // when & then
        MatchException matchException = assertThrows(MatchException.class, () ->
                matchFacade.acceptMatch(sender.getId(), pendingMatch.getId()));
        assertThat(matchException.getErrorCode()).isEqualTo(MatchErrorCode.MATCH_NOT_RECEIVER);
    }

    @Test
    @DisplayName("이미 처리된 매치를 수락하려면 예외가 발생한다")
    void acceptAlreadyProcessedMatch() {
        // given
        pendingMatch.accept();
        pendingMatchRepository.save(pendingMatch);

        // when & then
        MatchException matchException = assertThrows(MatchException.class, () ->
                matchFacade.acceptMatch(receiver.getId(), pendingMatch.getId()));
        assertThat(matchException.getErrorCode()).isEqualTo(MatchErrorCode.MATCH_NOT_PENDING);
    }
} 