package com.smashrating.rating.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Tier {
    UNRANKED(0, 999),

    BRONZE_4(1000, 1099),
    BRONZE_3(1100, 1199),
    BRONZE_2(1200, 1299),
    BRONZE_1(1300, 1399),

    SILVER_4(1400, 1499),
    SILVER_3(1500, 1599),
    SILVER_2(1600, 1699),
    SILVER_1(1700, 1799),

    GOLD_4(1800, 1899),
    GOLD_3(1900, 1999),
    GOLD_2(2000, 2099),
    GOLD_1(2100, 2199),

    PLATINUM_4(2200, 2299),
    PLATINUM_3(2300, 2399),
    PLATINUM_2(2400, 2499),
    PLATINUM_1(2500, 2599),

    DIAMOND_4(2600, 2699),
    DIAMOND_3(2700, 2799),
    DIAMOND_2(2800, 2899),
    DIAMOND_1(2900, 2999),

    MASTER(3000, Integer.MAX_VALUE)
    ;

    private final int minScore;
    private final int maxScore;

    public static Tier fromScore(int score) {
        for (Tier tier : values()) {
            if (score >= tier.minScore && score <= tier.maxScore) {
                return tier;
            }
        }
        return UNRANKED;
    }

    public static Tier getTierByScoreAndMatches(double score, int matchCount) {
        if (matchCount < 5) {
            return UNRANKED;
        }

        return fromScore((int) score);
    }
}
