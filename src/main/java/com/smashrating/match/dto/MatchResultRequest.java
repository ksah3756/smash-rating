package com.smashrating.match.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MatchResultRequest(
        @NotNull
        @Schema(description = "마지막 매치 결과 ID", example = "1")
        Long lastMatchResultId,

        @Min(value = 1, message = "size는 1보다 커야 합니다.")
        @Max(value = 100, message = "size는 100보다 작아야 합니다.")
        @Schema(description = "가져올 매치 결과 개수", example = "20")
        int size
) {

}
