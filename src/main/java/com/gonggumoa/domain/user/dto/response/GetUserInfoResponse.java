package com.gonggumoa.domain.user.dto.response;

import com.gonggumoa.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record GetUserInfoResponse (
        @Schema(description = "회원 닉네임", example = "쥬니")
        String nickname,

        @Schema(description = "회원 프로필 이미지 url", example = "https://s3.ap-northeast-2.amazonaws.com/bucket/profile/123.png")
        String profileImage
) {
    public static GetUserInfoResponse from(User user) {
        return new GetUserInfoResponse(user.getNickname(), user.getProfileImageUrl());
    }
}
