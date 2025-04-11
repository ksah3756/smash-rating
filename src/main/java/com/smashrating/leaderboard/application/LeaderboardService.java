package com.smashrating.leaderboard.application;

import com.smashrating.auth.dto.UserPrinciple;
import com.smashrating.leaderboard.LeaderboardRepository;
import com.smashrating.leaderboard.dto.RankResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaderboardService {
    private final LeaderboardRepository leaderboardRepository;

    public RankResponse getMyRank(UserPrinciple user) {
        Double score = leaderboardRepository.getScore(user.getUsername());
        Long rank = leaderboardRepository.getRank(user.getUsername());
        return RankResponse.of(user.getUsername(), score, rank);
    }
}
