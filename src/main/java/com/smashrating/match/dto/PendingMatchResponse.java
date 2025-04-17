package com.smashrating.match.dto;

public record PendingMatchResponse(
    Long id,
    String opponentUsername,
    String opponentNickName,
    Double opponentScore,
    String opponentTier
) {
    public static PendingMatchResponse of(
            Long id,
            String opponentUsername,
            String opponentNickName,
            Double opponentScore,
            String opponentTier
    ) {
        return new PendingMatchResponse(id, opponentUsername, opponentNickName, opponentScore, opponentTier);
    }
}
