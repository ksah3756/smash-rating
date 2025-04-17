package com.smashrating.match.application.command;

import com.smashrating.match.application.query.PendingMatchQueryService;
import com.smashrating.match.domain.PendingMatch;
import com.smashrating.match.infrastructure.PendingMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PendingMatchCommandService {

    private final PendingMatchRepository pendingMatchRepository;
    private final PendingMatchQueryService pendingMatchQueryService;

    public void createPendingMatch(Long senderId, Long receiverId) {
        PendingMatch pendingMatch = PendingMatch.create(
                senderId,
                receiverId
        );
        pendingMatchRepository.save(pendingMatch);
    }

    public void acceptPendingMatch(PendingMatch match) {
        match.accept();
    }

}
