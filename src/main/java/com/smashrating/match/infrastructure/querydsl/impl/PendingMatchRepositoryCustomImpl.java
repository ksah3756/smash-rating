package com.smashrating.match.infrastructure.querydsl.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smashrating.match.domain.QPendingMatch;
import com.smashrating.match.dto.PendingMatchResponse;
import com.smashrating.match.enums.PendingMatchStatus;
import com.smashrating.match.infrastructure.querydsl.PendingMatchRepositoryCustom;
import com.smashrating.rating.domain.QRating;
import com.smashrating.user.domain.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.types.Projections.*;

@Repository
@RequiredArgsConstructor
public class PendingMatchRepositoryCustomImpl implements PendingMatchRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PendingMatchResponse> getReceivedPendingMatch(Long receiveUserId) {
        QPendingMatch pendingMatch = QPendingMatch.pendingMatch;
        QUser opponent = QUser.user;
        QRating opponentRating = QRating.rating;

        // TODO: PendingMatchStatus에 인덱스 걸기?
        return jpaQueryFactory.select(constructor(
                    PendingMatchResponse.class,
                    opponent.username,
                    opponent.nickname,
                    opponentRating.score,
                    opponentRating.tier.stringValue()
            ))
            .from(pendingMatch)
            .join(opponent).on(pendingMatch.sendUserId.eq(opponent.id))
            .join(opponentRating).on(opponent.id.eq(opponentRating.id))
            .where(pendingMatch.receiveUserId.eq(receiveUserId)
                    .and(pendingMatch.status.eq(PendingMatchStatus.PENDING))
            )
            .fetch();
    }

    @Override
    public List<PendingMatchResponse> getSentPendingMatch(Long sendUserId) {
        QPendingMatch pendingMatch = QPendingMatch.pendingMatch;
        QUser opponent = QUser.user;
        QRating rating = QRating.rating;

        // TODO: PendingMatchStatus에 인덱스 걸기?
        return jpaQueryFactory.select(constructor(
                        PendingMatchResponse.class,
                        opponent.username,
                        opponent.nickname,
                        rating.score,
                        rating.tier.stringValue()
                ))
                .from(pendingMatch)
                .join(opponent).on(pendingMatch.receiveUserId.eq(opponent.id))
                .join(rating).on(opponent.id.eq(rating.id))
                .where(pendingMatch.sendUserId.eq(sendUserId)
                        .and(pendingMatch.status.eq(PendingMatchStatus.PENDING))
                )
                .fetch();
    }
}
