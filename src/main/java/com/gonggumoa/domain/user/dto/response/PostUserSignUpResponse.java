package com.gonggumoa.domain.user.dto.response;

public record PostUserSignUpResponse(Long id) {
    public static PostUserSignUpResponse of(Long id) {
        return new PostUserSignUpResponse(id);
    }
}
