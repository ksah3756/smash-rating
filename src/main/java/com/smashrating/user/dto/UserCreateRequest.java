package com.smashrating.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.NoArgsConstructor;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record UserCreateRequest(
        @Schema(description = "로그인아이디", example = "test", requiredMode = REQUIRED)
        @NotBlank @Size(min = 1, max = 50, message = "로그인아이디는 50자 이내여야 합니다.")
        String username,

        @Schema(description = "비밀번호", example = "1234", requiredMode = REQUIRED)
        @NotBlank @Size(min = 4, max = 20, message = "비밀번호는 4자 이상, 20자 이내여야 합니다.")
        String password,

        @NotBlank @Size(min = 1, max = 30, message = "실명은 30자 이내여야 합니다.")
        @Schema(description = "이름", example = "김상호", requiredMode = REQUIRED)
        String realName,

        @NotBlank @Size(min = 1, max = 30, message = "별명은 30자 이내여야 합니다.")
        @Schema(description = "이름", example = "지존", requiredMode = REQUIRED)
        String nickname,

        @NotBlank
        String email
) {
}
