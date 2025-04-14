package com.smashrating.match.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name = "user_id1")),
            @AttributeOverride(name = "score", column = @Column(name = "user1_score"))
    })
    private MatchUserInfo user1;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name = "user_id2")),
            @AttributeOverride(name = "score", column = @Column(name = "user2_score"))
    })
    private MatchUserInfo user2;

    @Enumerated(EnumType.STRING)
    @NotNull
    private MatchStatus status;

    @NotNull
    private int updatedScore;
}
