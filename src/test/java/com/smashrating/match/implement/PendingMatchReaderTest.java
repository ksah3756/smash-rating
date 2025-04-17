package com.smashrating.match.implement;

import com.smashrating.match.application.query.PendingMatchQueryService;
import com.smashrating.match.domain.PendingMatch;
import com.smashrating.match.dto.PendingMatchResponse;
import com.smashrating.match.infrastructure.FakePendingMatchRepository;
import com.smashrating.match.infrastructure.PendingMatchRepository;
import com.smashrating.rating.domain.Rating;
import com.smashrating.rating.infrastructure.FakeRatingRepository;
import com.smashrating.rating.infrastructure.RatingRepository;
import com.smashrating.user.UserTestFactory;
import com.smashrating.user.domain.User;
import com.smashrating.user.infrastructure.FakeUserRepository;
import com.smashrating.user.infrastructure.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class PendingMatchReaderTest {

    private UserRepository userRepository;
    private RatingRepository ratingRepository;
    private PendingMatchRepository pendingMatchRepository;
    private PendingMatchQueryService pendingMatchReader;

    private User sendUser, receiveUser;

    @BeforeEach
    void setUp() {
        userRepository = new FakeUserRepository();
        ratingRepository = new FakeRatingRepository();
        pendingMatchRepository = new FakePendingMatchRepository(userRepository, ratingRepository);
        pendingMatchReader = new PendingMatchQueryService(pendingMatchRepository);

        initPendingMatchData();
    }

    private void initPendingMatchData() {
        User user1 = UserTestFactory.createUser1();
        User user2 = UserTestFactory.createUser2();
        sendUser = userRepository.save(user1);
        receiveUser = userRepository.save(user2);

        Rating sendUserRating = Rating.builder()
                .id(sendUser.getId())
                .user(sendUser)
                .build();
        Rating receiveUserRating = Rating.builder()
                .id(receiveUser.getId())
                .user(receiveUser)
                .build();

        ratingRepository.save(sendUserRating);
        ratingRepository.save(receiveUserRating);

        PendingMatch pendingMatch = PendingMatch.create(sendUser.getId(), receiveUser.getId());
        pendingMatchRepository.save(pendingMatch);
    }

    @Test
    @DisplayName("요청받은 매치를 조회한다.")
    void getReceivedPendingMatch() {
        // when
        List<PendingMatchResponse> receivedPendingMatch = pendingMatchReader.getReceivedPendingMatch(receiveUser.getId());

        // then
        assertThat(receivedPendingMatch).hasSize(1);
        assertThat(receivedPendingMatch.get(0).opponentUsername()).isEqualTo(sendUser.getUsername());
    }

    @Test
    void getSentPendingMatch() {
        // when
        List<PendingMatchResponse> sentPendingMatch = pendingMatchReader.getSentPendingMatch(sendUser.getId());

        // then
        assertThat(sentPendingMatch).hasSize(1);
        assertThat(sentPendingMatch.get(0).opponentUsername()).isEqualTo(receiveUser.getUsername());
    }
}