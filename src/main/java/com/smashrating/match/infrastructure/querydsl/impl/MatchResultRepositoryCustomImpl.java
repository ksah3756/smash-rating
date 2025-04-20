package com.smashrating.match.infrastructure.querydsl.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.smashrating.match.domain.QMatchResult;
import com.smashrating.match.dto.MatchResultResponse;
import com.smashrating.match.infrastructure.querydsl.MatchResultRepositoryCustom;
import com.smashrating.rating.domain.QRating;
import com.smashrating.user.domain.QUser;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MatchResultRepositoryCustomImpl implements MatchResultRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MatchResultResponse> getMatchHistory(Long userId, Long lastMatchResultId, int size) {
        QMatchResult matchResult = QMatchResult.matchResult;
        QUser opponent = QUser.user;

        return jpaQueryFactory.select(Projections.constructor(
                        MatchResultResponse.class,
                        matchResult.id,
                        opponent.username,
                        opponent.nickname,
                        matchResult.userInfo.gameScore,
                        matchResult.opponentInfo.gameScore,
                        matchResult.updatedRatingScore)
                )
                .from(matchResult)
                .join(opponent).on(opponent.id.eq(matchResult.opponentInfo.userId))
                .where(matchResult.userInfo.userId.eq(userId)
                        .and(cursorId(lastMatchResultId)))
                .orderBy(matchResult.id.desc())
                .limit(size)
                .fetch();
    }

    private BooleanExpression cursorId(Long lastId) {
        return lastId != null ? QMatchResult.matchResult.id.lt(lastId) : null;
    }
}
