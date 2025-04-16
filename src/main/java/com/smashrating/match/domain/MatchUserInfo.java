package com.smashrating.match.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Embeddable
@Getter
public class MatchUserInfo {
    @NotNull
    private Long userId;

    @NotNull
    private int gameScore;
}
