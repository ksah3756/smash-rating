package com.smashrating.match.infrastructure.querydsl;

import com.smashrating.match.dto.MatchResultResponse;

import java.util.List;

public interface MatchResultRepositoryCustom {
    List<MatchResultResponse> getMatchHistory(Long userId, Long lastMatchResultId, int size);
}
