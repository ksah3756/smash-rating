package com.smashrating.match.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record PendingMatchCreateRequest (
        @NotNull
        @Schema(description = "상대방의 id", example = "ksah3756")
        String receiverUsername
) {
}
