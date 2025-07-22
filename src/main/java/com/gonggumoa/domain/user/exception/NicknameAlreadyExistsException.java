package com.gonggumoa.domain.user.exception;

import com.gonggumoa.global.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class NicknameAlreadyExistsException extends RuntimeException {
    private final ResponseStatus exceptionStatus;

    public NicknameAlreadyExistsException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}