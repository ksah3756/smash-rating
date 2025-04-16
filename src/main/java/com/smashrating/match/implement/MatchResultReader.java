package com.smashrating.match.implement;

import com.smashrating.match.dto.MatchResultResponse;
import com.smashrating.match.infrastructure.MatchResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MatchResultReader {

    private final MatchResultRepository matchResultRepository;

    public List<MatchResultResponse> getMatchHistory(Long userId, Long lastMatchResultId, int size) {
        return matchResultRepository.getMatchHistory(userId, lastMatchResultId, size);
    }
}
