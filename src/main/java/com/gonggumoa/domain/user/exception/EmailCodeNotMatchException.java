package com.gonggumoa.domain.user.exception;

import com.gonggumoa.global.response.status.ResponseStatus;

public class EmailCodeNotMatchException extends RuntimeException {
    private final ResponseStatus exceptionStatus;

    public EmailCodeNotMatchException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}
