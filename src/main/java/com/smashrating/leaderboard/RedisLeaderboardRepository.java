package com.smashrating.leaderboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smashrating.leaderboard.dto.RankEntry;
import com.smashrating.user.exception.UserErrorCode;
import com.smashrating.user.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class RedisLeaderboardRepository implements LeaderboardRepository {
    private final RedisTemplate<String, String> redisTemplate;

    private static final String LEADERBOARD_KEY = "leaderboard";

    @Override
    public void addScore(String username, double score) {
        redisTemplate.opsForZSet().add(LEADERBOARD_KEY, username, score);
    }

    @Override
    public void updateScore(String username, double score) {
        redisTemplate.opsForZSet().add(LEADERBOARD_KEY, username, score);
    }

    @Override
    public void removeScore(String username) {
        // Redis에서 점수 제거하는 로직
        redisTemplate.opsForZSet().remove(LEADERBOARD_KEY, username);
    }

    @Override
    public Double getScore(String username) {
        Double score = redisTemplate.opsForZSet().score(LEADERBOARD_KEY, username);
        if(score == null) {
            throw new UserException(UserErrorCode.USER_NOT_FOUND);
        }
        return score;
    }

    @Override
    public Long getRank(String username) {
        Long rank = redisTemplate.opsForZSet().reverseRank(LEADERBOARD_KEY, username);
        if(rank == null) {
            throw new UserException(UserErrorCode.USER_NOT_FOUND);
        }
        return rank;
    }

    @Override
    public List<RankEntry> getAllRankings(int page, int size) {
        // Redis에서 모든 랭킹 가져오는 로직
        int start = (page - 1) * size;
        int end = start + size - 1;
        Set<ZSetOperations.TypedTuple<String>> rangeWithScores =
                redisTemplate.opsForZSet()
                        .reverseRangeWithScores(LEADERBOARD_KEY, start, end);

        if (rangeWithScores == null) return List.of();

        return rangeWithScores
                .stream()
                .map(entry -> RankEntry.of(entry.getValue(), entry.getScore()))
                .toList();
    }
}
