package com.smashrating.match.application;

import com.smashrating.match.domain.PendingMatch;
import com.smashrating.match.enums.PendingMatchStatus;
import com.smashrating.match.exception.MatchErrorCode;
import com.smashrating.match.exception.MatchException;
import org.springframework.stereotype.Component;

@Component
public class PendingMatchValidateService {

    public void checkIfSenderReceiverSame(Long senderId, Long receiverId) {
        if (senderId.equals(receiverId)) {
            throw new MatchException(MatchErrorCode.MATCH_ILLEGAL_REQUEST);
        }
    }

    public void validateReceiver(PendingMatch match, Long userId) {
        if (!match.getReceiveUserId().equals(userId)) {
            throw new MatchException(MatchErrorCode.MATCH_NOT_RECEIVER);
        }
    }

    public void validatePendingStatus(PendingMatch match) {
        if (match.getStatus() != PendingMatchStatus.PENDING) {
            throw new MatchException(MatchErrorCode.MATCH_NOT_PENDING);
        }
    }

}
