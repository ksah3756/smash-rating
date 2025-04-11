package com.smashrating.leaderboard.dto;

public record RankEntry (
        String username,
        double score
) {
    public static RankEntry of(String username, double score) {
        return new RankEntry(username, score);
    }
}
