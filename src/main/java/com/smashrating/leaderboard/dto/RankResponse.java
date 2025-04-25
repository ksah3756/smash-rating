package com.smashrating.leaderboard.dto;

public record RankResponse (
        String username,
        double score,
        long rank
) {
    public static RankResponse of(String username, double score, long rank) {
        return new RankResponse(username, score, rank);
    }
}
