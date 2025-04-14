package com.smashrating.leaderboard.application;

import com.smashrating.leaderboard.dto.RankEntry;

import java.util.List;

public interface LeaderboardRepository {
    void addScore(String username, double score);
    void updateScore(String username, double score);
    void removeScore(String username);
    Double getScore(String username);
    Long getRank(String username);
    List<RankEntry> getAllRankings(int page, int size);
}
