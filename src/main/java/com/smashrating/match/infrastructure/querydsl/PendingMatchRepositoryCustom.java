package com.smashrating.match.infrastructure.querydsl;

import com.smashrating.match.dto.PendingMatchResponse;

import java.util.List;

public interface PendingMatchRepositoryCustom {
    List<PendingMatchResponse> getReceivedPendingMatch(Long receiveUserId);
    List<PendingMatchResponse> getSentPendingMatch(Long sendUserId);
}
