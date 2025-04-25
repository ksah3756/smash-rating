package com.smashrating.match.infrastructure;

import com.smashrating.match.domain.MatchResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMatchResultRepository extends JpaRepository<MatchResult, Long> {
}
