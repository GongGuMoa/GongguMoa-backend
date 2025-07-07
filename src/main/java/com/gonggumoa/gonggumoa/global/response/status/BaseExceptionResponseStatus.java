package com.gonggumoa.gonggumoa.global.response.status;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum BaseExceptionResponseStatus implements ResponseStatus {
    SUCCESS(20000, "요청에 성공했습니다.", HttpStatus.OK),
    BAD_REQUEST(40000, "유효하지 않은 요청입니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND(40400, "존재하지 않는 API입니다.", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR(50000, "서버 내부 오류입니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    ;

    private final boolean success = false;
    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    @Override
    public boolean getSuccess() {
        return success;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
