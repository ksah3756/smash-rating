package com.smashrating.match.implement;

import com.smashrating.match.domain.PendingMatch;
import com.smashrating.match.dto.PendingMatchResponse;
import com.smashrating.match.infrastructure.PendingMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PendingMatchReader {
    private final PendingMatchRepository pendingMatchRepository;

    public List<PendingMatchResponse> getReceivedPendingMatch(Long receiveUserId) {
        return pendingMatchRepository.getReceivedPendingMatch(receiveUserId);
    }

    public List<PendingMatchResponse> getSentPendingMatch(Long sendUserId) {
        return pendingMatchRepository.getSentPendingMatch(sendUserId);
    }
}
