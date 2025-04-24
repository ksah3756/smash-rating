package com.smashrating.match.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record MatchResultRequest(
        Long lastMatchResultId,
        @Min(1) @Max(100) int size
) {

}
