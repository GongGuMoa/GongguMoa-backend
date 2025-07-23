package com.gonggumoa.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PostUserLoginRequest (
        @Schema(description = "이메일 또는 전화번호", example = "test@email.com")
        @NotBlank
        String identifier,
        @Schema(description = "비밀번호 (리프레시 토큰 로그인 시 null 허용)", example = "abc12345!", nullable = true)
        String password
) { }
