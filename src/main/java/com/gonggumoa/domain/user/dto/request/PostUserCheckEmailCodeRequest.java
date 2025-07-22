package com.gonggumoa.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PostUserCheckEmailCodeRequest(
        @Schema(description = "이메일", example = "test@email.com")
        @NotBlank
        @Email
        String email,
        @Schema(description = "인증코드", example = "288977")
        @NotBlank
        String code
) {
}
