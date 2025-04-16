package com.smashrating.match.infrastructure;

import com.smashrating.match.domain.PendingMatch;
import com.smashrating.match.infrastructure.querydsl.PendingMatchRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PendingMatchRepository extends JpaRepository<PendingMatch, Long>, PendingMatchRepositoryCustom {
    List<PendingMatch> findBySendUserId(Long requestUserId);
    List<PendingMatch> findByReceiveUserId(Long receiveUserId);
}