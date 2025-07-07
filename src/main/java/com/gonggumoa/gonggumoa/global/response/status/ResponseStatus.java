package com.gonggumoa.gonggumoa.global.response.status;

import org.springframework.http.HttpStatus;

public interface ResponseStatus {
    boolean getSuccess();
    int getCode();
    String getMessage();
    HttpStatus getHttpStatus();

}
