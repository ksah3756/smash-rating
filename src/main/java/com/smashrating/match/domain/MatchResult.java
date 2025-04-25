package com.smashrating.match.domain;

import com.smashrating.common.BaseEntity;
import com.smashrating.match.enums.MatchResultStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class MatchResult extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name = "user_id")),
            @AttributeOverride(name = "gameScore", column = @Column(name = "user_score"))
    })
    private MatchUserInfo userInfo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name = "opponent_id")),
            @AttributeOverride(name = "gameScore", column = @Column(name = "opponent_score"))
    })
    private MatchUserInfo opponentInfo;

    @Enumerated(EnumType.STRING)
    @NotNull
    private MatchResultStatus status;

    @NotNull
    private Double updatedRatingScore;

    public void complete() {
        this.status = MatchResultStatus.COMPLETED;
    }

    public static MatchResult create(MatchUserInfo userInfo, MatchUserInfo opponentInfo, Double updatedRatingScore) {
        return MatchResult.builder()
                .userInfo(userInfo)
                .opponentInfo(opponentInfo)
                .status(MatchResultStatus.PENDING)
                .updatedRatingScore(updatedRatingScore)
                .build();
    }
}
