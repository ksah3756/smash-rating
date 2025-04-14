package com.smashrating.leaderboard.application;

import com.smashrating.auth.dto.UserPrinciple;
import com.smashrating.leaderboard.dto.RankEntry;
import com.smashrating.leaderboard.dto.RankResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class LeaderboardService {
    private final LeaderboardRepository leaderboardRepository;

    public RankResponse getMyRank(UserPrinciple user) {
        Double score = leaderboardRepository.getScore(user.getUsername());
        Long rank = leaderboardRepository.getRank(user.getUsername());
        return RankResponse.of(user.getUsername(), score, rank);
    }

    public List<RankResponse> getRankListByPage(int page, int size) {
        // page * size + 1 ~ page * size + size 까지가 순위
        int startRank = (page - 1) * size + 1;
        List<RankEntry> pageRankings = leaderboardRepository.getAllRankings(page, size);

        return getRankResponseList(pageRankings, startRank);
    }

    private static List<RankResponse> getRankResponseList(List<RankEntry> pageRankings, int startRank) {
        return IntStream.range(0, pageRankings.size())
                .mapToObj(i -> {
                    RankEntry entry = pageRankings.get(i);
                    int rank = startRank + i;
                    return RankResponse.of(entry.username(), entry.score(), rank);
                })
                .toList();
    }

    public Double updateScore(String username, double score) {
        leaderboardRepository.updateScore(username, score);
        return leaderboardRepository.getScore(username);
    }
}
