package com.smashrating.match.infrastructure.impl;

import com.smashrating.match.domain.MatchResult;
import com.smashrating.match.dto.MatchResultResponse;
import com.smashrating.match.infrastructure.JpaMatchResultRepository;
import com.smashrating.match.infrastructure.MatchResultRepository;
import com.smashrating.match.infrastructure.querydsl.MatchResultRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MatchResultRepositoryImpl implements MatchResultRepository {

    private final JpaMatchResultRepository matchResultRepository;
    private final MatchResultRepositoryCustom matchResultRepositoryCustom;

    @Override
    public List<MatchResultResponse> getMatchHistory(Long userId, Long lastMatchResultId, int size) {
        return matchResultRepositoryCustom.getMatchHistory(userId, lastMatchResultId, size);
    }

    @Override
    public MatchResult save(MatchResult matchResult) {
        return matchResultRepository.save(matchResult);
    }
}
