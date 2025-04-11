package com.smashrating.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public record UserCreateRequest(
        @Schema(description = "로그인아이디", example = "organking", requiredMode = REQUIRED)
        @NotBlank @Size(min = 1, max = 50, message = "로그인아이디는 50자 이내여야 합니다.")
        String username,

        @Schema(description = "비밀번호", example = "organking1234", requiredMode = REQUIRED)
        @NotBlank @Size(min = 4, max = 20, message = "비밀번호는 4자 이상, 20자 이내여야 합니다.")
        String password,

        @NotBlank @Size(min = 1, max = 50, message = "이름은 50자 이내여야 합니다.")
        @Schema(description = "이름", example = "오르킹", requiredMode = REQUIRED)
        String name,

        @NotBlank
        String email
) {
}
