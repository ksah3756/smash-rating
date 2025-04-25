package com.smashrating.match.domain;

import com.smashrating.match.enums.PendingMatchStatus;

public class PendingMatchStub extends PendingMatch {
    private String opponentUsername;
    private String opponentNickname;
    private Double opponentScore;
    private String opponentTier;

    public String getOpponentUsername() {
        return opponentUsername;
    }

    public String getOpponentNickname() {
        return opponentNickname;
    }

    public Double getOpponentScore() {
        return opponentScore;
    }

    public String getOpponentTier() {
        return opponentTier;
    }

    public PendingMatchStub(Long id, Long sendUserId, Long receiveUserId, PendingMatchStatus status,
                            String opponentUsername, String opponentNickname, Double opponentScore, String opponentTier) {
        super(id, sendUserId, receiveUserId, status);
        this.opponentUsername = opponentUsername;
        this.opponentNickname = opponentNickname;
        this.opponentScore = opponentScore;
        this.opponentTier = opponentTier;
    }
}
