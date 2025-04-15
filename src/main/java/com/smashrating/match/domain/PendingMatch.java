package com.smashrating.match.domain;

import com.smashrating.common.BaseEntity;
import com.smashrating.match.enums.PendingMatchStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(indexes = {
        @Index(name = "idx_request_user_id", columnList = "request_user_id"),
        @Index(name = "idx_receive_user_id", columnList = "receive_user_id")
})
public class PendingMatch extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long sendUserId;

    @NotNull
    private Long receiveUserId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PendingMatchStatus status;
}
