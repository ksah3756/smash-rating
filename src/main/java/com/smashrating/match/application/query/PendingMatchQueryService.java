package com.smashrating.match.application.query;

import com.smashrating.match.domain.PendingMatch;
import com.smashrating.match.dto.PendingMatchResponse;
import com.smashrating.match.exception.MatchErrorCode;
import com.smashrating.match.exception.MatchException;
import com.smashrating.match.infrastructure.PendingMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PendingMatchQueryService {
    private final PendingMatchRepository pendingMatchRepository;

    public List<PendingMatchResponse> getReceivedPendingMatch(Long receiveUserId) {
        return pendingMatchRepository.getReceivedPendingMatch(receiveUserId);
    }

    public List<PendingMatchResponse> getSentPendingMatch(Long sendUserId) {
        return pendingMatchRepository.getSentPendingMatch(sendUserId);
    }

    public PendingMatch getPendingMatchById(Long matchId) {
        return pendingMatchRepository.findById(matchId).orElseThrow(() -> new MatchException(MatchErrorCode.MATCH_NOT_FOUND));
    }
}
