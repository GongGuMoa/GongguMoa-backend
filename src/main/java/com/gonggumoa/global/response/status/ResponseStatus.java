package com.gonggumoa.global.response.status;

import org.springframework.http.HttpStatus;

public interface ResponseStatus {
    int getCode();
    String getMessage();

}
