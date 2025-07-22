package com.gonggumoa.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PostUserEmailCodeRequest(
        @Schema(description = "이메일", example = "test@email.com")
        @NotBlank
        @Email
        String email
) { }
