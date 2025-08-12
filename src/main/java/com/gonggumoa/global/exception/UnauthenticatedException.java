package com.gonggumoa.global.exception;

import com.gonggumoa.global.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class UnauthenticatedException extends RuntimeException {
    private final ResponseStatus exceptionStatus;

    public UnauthenticatedException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}
