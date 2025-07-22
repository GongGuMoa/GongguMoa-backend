package com.gonggumoa.global.exception;

import com.gonggumoa.global.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private final ResponseStatus status;

    public BadRequestException(ResponseStatus status) {
        super(status.getMessage());
        this.status = status;
    }
}
