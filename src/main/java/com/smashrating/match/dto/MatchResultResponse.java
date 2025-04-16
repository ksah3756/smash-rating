package com.smashrating.match.dto;

public record MatchResultResponse(
        String opponentUsername,
        String opponentName,
        int myScore,
        int opponentScore,
        Double updatedRatingScore
) {
}
