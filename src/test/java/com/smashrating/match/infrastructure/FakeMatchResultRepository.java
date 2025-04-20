package com.smashrating.match.infrastructure;

import com.smashrating.match.domain.MatchResult;
import com.smashrating.match.dto.MatchResultResponse;
import com.smashrating.match.enums.MatchResultStatus;
import com.smashrating.user.domain.User;
import com.smashrating.user.exception.UserErrorCode;
import com.smashrating.user.exception.UserException;
import com.smashrating.user.infrastructure.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FakeMatchResultRepository implements MatchResultRepository {
    private final UserRepository userRepository;

    private final List<MatchResult> matchResults = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong sequence = new AtomicLong(1);

    public FakeMatchResultRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<MatchResultResponse> getMatchHistory(Long userId, Long lastMatchResultId, int size) {
        return matchResults.stream()
                .filter(matchResult -> matchResult.getId() > lastMatchResultId)
                .filter(matchResult -> matchResult.getUserInfo().getUserId().equals(userId))
                .filter(matchResult -> matchResult.getStatus().equals(MatchResultStatus.COMPLETED))
                .map(matchResult -> {
                    User opponent = userRepository.findById(matchResult.getOpponentInfo().getUserId())
                            .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
                    return new MatchResultResponse(
                        matchResult.getId(),
                        opponent.getUsername(),
                        opponent.getNickname(),
                        matchResult.getUserInfo().getGameScore(),
                        matchResult.getOpponentInfo().getGameScore(),
                        matchResult.getUpdatedRatingScore()
                    );
                })
                .limit(size)
                .toList();
    }

    @Override
    public MatchResult save(MatchResult matchResult) {
        MatchResult savedMatchResult = MatchResult.builder()
                .id(sequence.getAndIncrement())
                .userInfo(matchResult.getUserInfo())
                .opponentInfo(matchResult.getOpponentInfo())
                .status(matchResult.getStatus())
                .updatedRatingScore(matchResult.getUpdatedRatingScore())
                .build();

        matchResults.add(savedMatchResult);
        return savedMatchResult;
    }

    public List<MatchResult> saveAll(List<MatchResult> matchResults) {
        List<MatchResult> savedMatchResults = new ArrayList<>();
        for (MatchResult matchResult : matchResults) {
            savedMatchResults.add(save(matchResult));
        }
        return savedMatchResults;
    }
}
