package com.smashrating.match.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class MatchUserInfo {
    @NotNull
    private Long userId;

    @NotNull
    private int score;
}
