package com.gonggumoa.gonggumoa.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

public record PostUserSignUpResponse(Long id) {
    public static PostUserSignUpResponse of(Long id) {
        return new PostUserSignUpResponse(id);
    }
}
