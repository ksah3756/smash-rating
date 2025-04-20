package com.smashrating.match.infrastructure;

import com.smashrating.match.domain.MatchResult;
import com.smashrating.match.dto.MatchResultResponse;

import java.util.List;

public interface MatchResultRepository {
    List<MatchResultResponse> getMatchHistory(Long userId, Long lastMatchResultId, int size);
    MatchResult save(MatchResult matchResult);
}
