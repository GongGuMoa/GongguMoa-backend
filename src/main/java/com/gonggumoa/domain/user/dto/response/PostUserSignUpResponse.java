package com.gonggumoa.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record PostUserSignUpResponse(
        @Schema(description = "회원가입 성공한 회원 ID", example = "1")
        Long id
) {
    public static PostUserSignUpResponse of(Long id) {
        return new PostUserSignUpResponse(id);
    }
}
