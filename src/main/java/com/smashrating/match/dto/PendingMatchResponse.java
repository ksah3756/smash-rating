package com.smashrating.match.dto;

public record PendingMatchResponse(
    String opponentUsername,
    String opponentNickName,
    Double opponentScore,
    String opponentTier
) {
    public static PendingMatchResponse of(
            String opponentUsername,
            String opponentNickName,
            Double opponentScore,
            String opponentTier
    ) {
        return new PendingMatchResponse(opponentUsername, opponentNickName, opponentScore, opponentTier);
    }
}
