package com.smashrating.match.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PendingMatchCreateRequest (
        @NotNull
        @Schema(description = "상대방의 id", example = "ksah3756")
        String opponentUsername
) {
}
