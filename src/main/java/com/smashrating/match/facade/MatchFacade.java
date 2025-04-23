package com.smashrating.match.facade;

import com.smashrating.auth.dto.UserDto;
import com.smashrating.match.application.PendingMatchValidateService;
import com.smashrating.match.application.command.PendingMatchCommandService;
import com.smashrating.match.domain.PendingMatch;
import com.smashrating.match.dto.MatchResultRequest;
import com.smashrating.match.dto.MatchResultResponse;
import com.smashrating.match.dto.PendingMatchCreateRequest;
import com.smashrating.match.dto.PendingMatchResponse;
import com.smashrating.match.application.query.MatchResultQueryService;
import com.smashrating.match.application.query.PendingMatchQueryService;
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
    private final PendingMatchValidateService pendingMatchValidateService;
    private final MatchResultQueryService matchResultQueryService;
    private final UserQueryService userQueryService;

    @Transactional(readOnly = true)
    public List<PendingMatchResponse> getReceivedMatches(UserDto userDto) {
        return pendingMatchQueryService.getReceivedPendingMatch(userDto.id());
    }

    @Transactional(readOnly = true)
    public List<PendingMatchResponse> getSentMatches(UserDto userDto) {
        return pendingMatchQueryService.getSentPendingMatch(userDto.id());
    }

    @Transactional(readOnly = true)
    public List<MatchResultResponse> getMatchHistory(UserDto userDto, MatchResultRequest matchResultRequest) {
        return matchResultQueryService.getMatchHistory(userDto.id(), matchResultRequest.lastMatchResultId(), matchResultRequest.size());
    }

    @Transactional
    public void createPendingMatch(UserDto userDto, PendingMatchCreateRequest matchRequest) {
        User opponent = userQueryService.getUserByUsername(matchRequest.opponentUsername());
        if (userDto.equals(opponent.getId())) {
            throw new MatchException(MatchErrorCode.MATCH_ILLEGAL_REQUEST);
        }
        pendingMatchCommandService.createPendingMatch(
                userDto.id(),
                opponent.getId()
        );
    }

    @Transactional
    public void acceptMatch(UserDto userDto, Long matchId) {
        PendingMatch match = pendingMatchQueryService.getPendingMatchById(matchId);
        validatePendingMatchStatusAndReceiver(userDto, match);

        pendingMatchCommandService.acceptPendingMatch(match);
    }

    @Transactional
    public void rejectMatch(UserDto userDto, Long matchId) {
        PendingMatch match = pendingMatchQueryService.getPendingMatchById(matchId);
        validatePendingMatchStatusAndReceiver(userDto, match);

        pendingMatchCommandService.rejectPendingMatch(match);
    }

    // PendingMatch의 상태가 Pending인지, PendingMatch의 receiver가 요청한 유저가 맞는지 확인
    private void validatePendingMatchStatusAndReceiver(UserDto userDto, PendingMatch match) {
        pendingMatchValidateService.validatePendingStatus(match);
        pendingMatchValidateService.validateReceiver(match, userDto.id());
    }
}
