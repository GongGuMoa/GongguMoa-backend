package com.gonggumoa.domain.user.exception;

import com.gonggumoa.global.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final ResponseStatus exceptionStatus;

    public UserNotFoundException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}
