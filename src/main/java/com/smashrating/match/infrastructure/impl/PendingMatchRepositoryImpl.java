package com.smashrating.match.infrastructure.impl;

import com.smashrating.match.domain.PendingMatch;
import com.smashrating.match.dto.PendingMatchResponse;
import com.smashrating.match.infrastructure.JpaPendingMatchRepository;
import com.smashrating.match.infrastructure.PendingMatchRepository;
import com.smashrating.match.infrastructure.querydsl.PendingMatchRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PendingMatchRepositoryImpl implements PendingMatchRepository {

    private final JpaPendingMatchRepository pendingMatchRepository;
    private final PendingMatchRepositoryCustom pendingMatchRepositoryCustom;

    @Override
    public PendingMatch save(PendingMatch pendingMatch) {
        return pendingMatchRepository.save(pendingMatch);
    }

    @Override
    public Optional<PendingMatch> findById(Long id) {
        return pendingMatchRepository.findById(id);
    }

    @Override
    public List<PendingMatchResponse> getReceivedPendingMatch(Long receiveUserId) {
        return pendingMatchRepositoryCustom.getReceivedPendingMatch(receiveUserId);
    }

    @Override
    public List<PendingMatchResponse> getSentPendingMatch(Long sendUserId) {
        return pendingMatchRepositoryCustom.getSentPendingMatch(sendUserId);
    }
}
