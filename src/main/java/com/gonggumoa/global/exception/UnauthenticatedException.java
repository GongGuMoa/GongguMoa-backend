package com.gonggumoa.global.exception;

import com.gonggumoa.global.response.status.ResponseStatus;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class UnauthenticatedException extends AuthenticationException {
    private final ResponseStatus exceptionStatus;

    public UnauthenticatedException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}
