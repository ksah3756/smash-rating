package com.smashrating.match.dto;

public record MatchResultResponse(
        Long id,
        String opponentUsername,
        String opponentName,
        int myScore,
        int opponentScore,
        Double updatedRatingScore
) {
}
