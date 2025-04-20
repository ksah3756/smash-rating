package com.smashrating.match.dto;

public record MatchResultResponse(
        Long id,
        String opponentUsername,
        String opponentNickname,
        int myGameScore,
        int opponentGameScore,
        Double updatedRatingScore
) {
}
