package com.gonggumoa.gonggumoa.global.exception;

import com.gonggumoa.gonggumoa.global.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ResponseStatus status;

    public CustomException(ResponseStatus status) {
        super(status.getMessage());
        this.status = status;
    }
}
