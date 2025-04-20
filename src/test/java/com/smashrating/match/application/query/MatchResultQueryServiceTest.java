package com.smashrating.match.application.query;

import com.smashrating.match.MatchResultTestFactory;
import com.smashrating.match.domain.MatchResult;
import com.smashrating.match.domain.MatchUserInfo;
import com.smashrating.match.dto.MatchResultResponse;
import com.smashrating.match.enums.MatchResultStatus;
import com.smashrating.match.infrastructure.FakeMatchResultRepository;
import com.smashrating.match.infrastructure.MatchResultRepository;
import com.smashrating.user.UserTestFactory;
import com.smashrating.user.domain.User;
import com.smashrating.user.infrastructure.FakeUserRepository;
import com.smashrating.user.infrastructure.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class MatchResultQueryServiceTest {

    private UserRepository userRepository;
    private MatchResultRepository matchResultRepository;
    private MatchResultQueryService matchResultQueryService;

    private User user, opponentUser;

    @BeforeEach
    void setUp() {
        userRepository = new FakeUserRepository();
        matchResultRepository = new FakeMatchResultRepository(userRepository);
        matchResultQueryService = new MatchResultQueryService(matchResultRepository);

        initMatchResultData();
    }

    private void initMatchResultData() {
        User user1 = UserTestFactory.createUser1();
        User user2 = UserTestFactory.createUser2();
        user = userRepository.save(user1);
        opponentUser = userRepository.save(user2);

        MatchResultTestFactory.createMatchResults(user.getId(), opponentUser.getId(), 30)
                .forEach(matchResultRepository::save);
    }
    @Test
    @DisplayName("마지막으로 조회한 매치 결과 ID 이후의 매치 결과를 size개 만큼 조회한다.")
    void getMatchHistory() {
        // when
        Long lastMatchResultId = 9L;
        int size = 10;
        List<MatchResultResponse> matchHistory = matchResultQueryService.getMatchHistory(user.getId(), lastMatchResultId, size);

        // then
        assertEquals(size, matchHistory.size());
        IntStream.range(0, matchHistory.size()).forEach(i -> {
            MatchResultResponse matchResultResponse = matchHistory.get(i);
            assertEquals(matchResultResponse.id(), lastMatchResultId+1+i);
            assertEquals(opponentUser.getUsername(), matchResultResponse.opponentUsername());
            assertEquals(opponentUser.getNickname(), matchResultResponse.opponentNickname());
            assertEquals(15, matchResultResponse.myGameScore());
            assertEquals(10, matchResultResponse.opponentGameScore());
            assertEquals(32.0, matchResultResponse.updatedRatingScore());
        });
    }
}