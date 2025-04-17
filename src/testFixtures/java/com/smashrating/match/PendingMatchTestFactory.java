package com.smashrating.match;

import com.smashrating.match.domain.PendingMatch;

public class PendingMatchTestFactory {
    public static PendingMatch createDefaultPendingMatch(Long sendUserId, Long receiveUserId) {
       return PendingMatch.create(
               sendUserId,
                receiveUserId
       );
    }
}
