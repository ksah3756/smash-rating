package com.smashrating.match.dto;

import com.smashrating.match.domain.PendingMatch;
import com.smashrating.rating.domain.Tier;
import lombok.AllArgsConstructor;

public record PendingMatchResponse(
    Long opponentUserId,
    String opponentUserName,
    Double opponentUserScore,
    String opponentUserTier
) {
    public static PendingMatchResponse of(
            Long opponentUserId,
            String opponentUserName,
            Double opponentUserScore,
            String opponentUserTier
    ) {
        return new PendingMatchResponse(opponentUserId, opponentUserName, opponentUserScore, opponentUserTier);
    }
}
