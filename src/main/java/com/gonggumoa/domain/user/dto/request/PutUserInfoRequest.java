package com.gonggumoa.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PutUserInfoRequest(
        @Schema(description = "변경할 닉네임", example = "쥬니3")
        @NotBlank
        String nickname,

        @Schema(description = "변경할 비밀번호", example = "newPassword123")
        @NotBlank
        String password,

        @Schema(description = "변경할 프로필 이미지 URL", example = "https://s3.ap-northeast-2.amazonaws.com/bucket/profile/123.png")
        @NotNull
        String profileImageUrl
) {
}
