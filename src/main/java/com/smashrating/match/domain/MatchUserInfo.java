package com.smashrating.match.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Embeddable
@Getter
public class MatchUserInfo {
    @NotNull
    private Long userId;

    @NotNull
    private int gameScore;

    public static MatchUserInfo of(Long userId, int gameScore) {
        MatchUserInfo matchUserInfo = new MatchUserInfo();
        matchUserInfo.userId = userId;
        matchUserInfo.gameScore = gameScore;
        return matchUserInfo;
    }
}
