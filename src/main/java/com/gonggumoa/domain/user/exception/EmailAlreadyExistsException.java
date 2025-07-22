package com.gonggumoa.domain.user.exception;

import com.gonggumoa.global.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class EmailAlreadyExistsException extends RuntimeException {
    private final ResponseStatus exceptionStatus;

    public EmailAlreadyExistsException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}