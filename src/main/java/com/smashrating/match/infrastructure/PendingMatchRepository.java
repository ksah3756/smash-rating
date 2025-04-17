package com.smashrating.match.infrastructure;

import com.smashrating.match.domain.PendingMatch;
import com.smashrating.match.dto.PendingMatchResponse;

import java.util.List;
import java.util.Optional;

public interface PendingMatchRepository {
    PendingMatch save(PendingMatch pendingMatch);

    Optional<PendingMatch> findById(Long id);

    List<PendingMatchResponse> getReceivedPendingMatch(Long receiveUserId);

    List<PendingMatchResponse> getSentPendingMatch(Long sendUserId);
}
