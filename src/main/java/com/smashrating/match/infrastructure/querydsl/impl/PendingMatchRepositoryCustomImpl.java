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

import java.util.List;

@RequiredArgsConstructor
public class PendingMatchRepositoryCustomImpl implements PendingMatchRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PendingMatchResponse> getReceivedPendingMatch(Long receiveUserId) {
        QPendingMatch pendingMatch = QPendingMatch.pendingMatch;
        QUser opponent = QUser.user;
        QRating rating = QRating.rating;

        // TODO: PendingMatchStatus에 인덱스 걸기?
        return jpaQueryFactory.select(Projections.constructor(
                    PendingMatchResponse.class,
                    opponent.id,
                    opponent.name,
                    rating.score,
                    rating.tier.stringValue()
            ))
            .from(pendingMatch)
            .join(opponent).on(pendingMatch.sendUserId.eq(opponent.id))
            .join(rating).on(opponent.id.eq(rating.id))
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
        return jpaQueryFactory.select(Projections.constructor(
                        PendingMatchResponse.class,
                        opponent.id,
                        opponent.name,
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
