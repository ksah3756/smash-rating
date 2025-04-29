package com.smashrating.rating.domain;

import com.smashrating.common.BaseEntity;
import com.smashrating.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Rating extends BaseEntity {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @NotNull
    @Builder.Default
    private Double score = 1000.0;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Tier tier = Tier.UNRANKED;

    @NotNull
    @Builder.Default
    private int totalMatchCount = 0;

    @NotNull
    @Builder.Default
    private int winMatchCount = 0;

    public static Rating create(User user) {
        return Rating.builder()
                .user(user)
                .build();
    }
}
