package com.smashrating.match.infrastructure.querydsl;

import com.smashrating.match.dto.MatchResultResponse;

import java.util.List;

public interface MatchResultRepositoryCustom {
    /**
     * Retrieves paginated match history for a specific user.
     *
     * @param userId The ID of the user whose match history is being retrieved
     * @param lastMatchResultId The ID of the last match result for cursor-based pagination (null for first page)
     * @param size Maximum number of results to return
     * @return List of match results ordered by most recent first
     */
    List<MatchResultResponse> getMatchHistory(Long userId, Long lastMatchResultId, int size);
}
