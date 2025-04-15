package com.smashrating.rating.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.smashrating.rating.domain.Tier.TierLevel.*;

@Getter
@RequiredArgsConstructor
public enum Tier {
    BRONZE_1(1000, 1099),
    BRONZE_2(1100, 1199),
    BRONZE_3(1200, 1299),
    BRONZE_4(1300, 1399),

    SILVER_1( 1400, 1499),
    SILVER_2( 1500, 1599),
    SILVER_3( 1600, 1699),
    SILVER_4( 1700, 1799),

    GOLD_1( 1800, 1899),
    GOLD_2( 1900, 1999),
    GOLD_3( 2000, 2099),
    GOLD_4( 2100, 2199),

    PLATINUM_1( 2200, 2299),
    PLATINUM_2( 2300, 2399),
    PLATINUM_3( 2400, 2499),
    PLATINUM_4( 2500, 2599),

    DIAMOND_1( 2600, 2699),
    DIAMOND_2( 2700, 2799),
    DIAMOND_3( 2800, 2899),
    DIAMOND_4( 2900, 2999),

    MASTER(3000, Integer.MAX_VALUE)
    ;

    private final int minScore;
    private final int maxScore;
}
