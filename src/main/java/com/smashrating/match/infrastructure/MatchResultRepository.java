package com.smashrating.match.infrastructure;

import com.smashrating.match.domain.MatchResult;
import com.smashrating.match.infrastructure.querydsl.MatchResultRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchResultRepository extends JpaRepository<MatchResult, Long>, MatchResultRepositoryCustom {
}
