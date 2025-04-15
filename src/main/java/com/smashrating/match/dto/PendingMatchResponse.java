package com.smashrating.match.dto;

import com.smashrating.match.domain.PendingMatch;

public record PendingMatchResponse(
    Long opponentUserId,
    Long opponentUserName,
    Double opponentUserScore
) {
    public static PendingMatchResponse of(
            Long opponentUserId,
            Long opponentUserName,
            Double opponentUserScore
    ) {
        return new PendingMatchResponse(opponentUserId, opponentUserName, opponentUserScore);
    }
}
