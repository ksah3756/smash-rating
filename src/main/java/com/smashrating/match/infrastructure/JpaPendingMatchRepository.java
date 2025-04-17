package com.smashrating.match.infrastructure;

import com.smashrating.match.domain.PendingMatch;
import com.smashrating.match.infrastructure.querydsl.PendingMatchRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPendingMatchRepository extends JpaRepository<PendingMatch, Long> {
}