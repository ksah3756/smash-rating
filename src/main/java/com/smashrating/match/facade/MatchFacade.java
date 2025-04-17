package com.smashrating.match.facade;

import com.smashrating.auth.dto.UserPrincipal;
import com.smashrating.match.application.command.PendingMatchCommandService;
import com.smashrating.match.domain.PendingMatch;
import com.smashrating.match.dto.MatchResultRequest;
import com.smashrating.match.dto.MatchResultResponse;
import com.smashrating.match.dto.PendingMatchCreateRequest;
import com.smashrating.match.dto.PendingMatchResponse;
import com.smashrating.match.application.query.MatchResultQueryService;
import com.smashrating.match.application.query.PendingMatchQueryService;
import com.smashrating.match.enums.PendingMatchStatus;
import com.smashrating.match.exception.MatchErrorCode;
import com.smashrating.match.exception.MatchException;
import com.smashrating.user.application.query.UserQueryService;
import com.smashrating.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchFacade {
    private final PendingMatchQueryService pendingMatchQueryService;
    private final PendingMatchCommandService pendingMatchCommandService;
    private final MatchResultQueryService matchResultQueryService;
    private final UserQueryService userQueryService;

    @Transactional(readOnly = true)
    public List<PendingMatchResponse> getReceivedMatches(Long userId) {
        return pendingMatchQueryService.getReceivedPendingMatch(userId);
    }

    @Transactional(readOnly = true)
    public List<PendingMatchResponse> getSentMatches(Long userId) {
        return pendingMatchQueryService.getSentPendingMatch(userId);
    }

    @Transactional(readOnly = true)
    public List<MatchResultResponse> getMatchHistory(Long userId, MatchResultRequest matchResultRequest) {
        return matchResultQueryService.getMatchHistory(userId, matchResultRequest.lastMatchResultId(), matchResultRequest.size());
    }

    @Transactional
    public void createPendingMatch(Long userId, PendingMatchCreateRequest matchRequest) {
        User opponent = userQueryService.getUserByUsername(matchRequest.opponentUsername());
        pendingMatchCommandService.createPendingMatch(
                userId,
                opponent.getId()
        );
    }

    @Transactional
    public void acceptMatch(Long userId, Long matchId) {
        PendingMatch match = pendingMatchQueryService.getPendingMatchById(matchId);
        if(!match.getReceiveUserId().equals(userId)) {
            throw new MatchException(MatchErrorCode.MATCH_NOT_RECEIVER);
        }
        if(match.getStatus() != PendingMatchStatus.PENDING) {
            throw new MatchException(MatchErrorCode.MATCH_ALREADY_ACCEPTED);
        }
        pendingMatchCommandService.acceptPendingMatch(match);
    }
}
