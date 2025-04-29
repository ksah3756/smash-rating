package com.smashrating.match;

import com.smashrating.match.domain.MatchResult;
import com.smashrating.match.domain.MatchUserInfo;
import com.smashrating.match.enums.MatchResultStatus;

import java.util.List;
import java.util.stream.IntStream;

public class MatchResultTestFactory {
    public static List<MatchResult> createMatchResults(Long userId, Long opponentUserId, int size) {
    return IntStream.range(0, size)
            .mapToObj(i -> MatchResult.create(
                    MatchUserInfo.of(userId, 15),
                    MatchUserInfo.of(opponentUserId, 10),
                    32.0
            ))
            .peek(MatchResult::complete)
            .toList();
    }
}
