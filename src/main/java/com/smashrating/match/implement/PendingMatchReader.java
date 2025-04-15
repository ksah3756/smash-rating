package com.smashrating.match.implement;

import com.smashrating.match.domain.PendingMatch;
import com.smashrating.match.infrastructure.PendingMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PendingMatchReader {
    private final PendingMatchRepository pendingMatchRepository;

    public List<PendingMatch> getReceivedPendingMatch(Long receiveUserId) {
        return pendingMatchRepository.findByReceiveUserId(receiveUserId);
    }

    public List<PendingMatch> getSentPendingMatch(Long sendUserId) {
        return pendingMatchRepository.findBySendUserId(sendUserId);
    }
}
