package com.smashrating.match.application;

import com.smashrating.auth.dto.UserPrincipal;
import com.smashrating.match.dto.MatchResultRequest;
import com.smashrating.match.dto.MatchResultResponse;
import com.smashrating.match.dto.PendingMatchResponse;
import com.smashrating.match.implement.MatchResultReader;
import com.smashrating.match.implement.PendingMatchReader;
import com.smashrating.user.domain.User;
import com.smashrating.user.implement.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final PendingMatchReader pendingMatchReader;
    private final MatchResultReader matchResultReader;

    public List<PendingMatchResponse> getReceivedMatches(UserPrincipal userPrincipal) {
        return pendingMatchReader.getReceivedPendingMatch(getUserId(userPrincipal));
    }

    public List<PendingMatchResponse> getSentMatches(UserPrincipal userPrincipal) {
        return pendingMatchReader.getSentPendingMatch(getUserId(userPrincipal));
    }
//
    public List<MatchResultResponse> getMatchHistory(UserPrincipal userPrincipal, MatchResultRequest matchResultRequest) {
        return matchResultReader.getMatchHistory(getUserId(userPrincipal), matchResultRequest.lastMatchResultId(), matchResultRequest.size());
    }

    private Long getUserId(UserPrincipal userPrincipal) {
        return userPrincipal.getId();
    }
}
