package com.smashrating.match.infrastructure;

import com.smashrating.match.domain.PendingMatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PendingMatchRepository extends JpaRepository<PendingMatch, Long> {
    List<PendingMatch> findBySendUserId(Long requestUserId);
    List<PendingMatch> findByReceiveUserId(Long receiveUserId);
}