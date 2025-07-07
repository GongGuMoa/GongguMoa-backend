package com.gonggumoa.gonggumoa.global.response.status;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum UserExceptionResponseStatus implements ResponseStatus {
    REQUIRED_FIELD_MISSING(60000, "필수 입력 항목이 누락되었습니다.", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTS(60001, "이미 존재하는 아이디입니다.", HttpStatus.CONFLICT),
    EMAIL_ALREADY_EXISTS(60003, "이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT),
    PHONENUM_ALREADY_EXISTS(60004, "이미 사용 중인 전화번호입니다.", HttpStatus.CONFLICT),
    INVALID_PASSWORD_FORMAT(60005, "비밀번호 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL_FORMAT(60006, "이메일 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_PHONE_NUMBER_FORMAT(60007, "전화번호 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_BIRTHDATE_FORMAT(60008, "생년월일 형식이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_BIRTHDATE(60009, "유효한 생년월일이 아닙니다.", HttpStatus.BAD_REQUEST),
    AGREEMENT_NOT_ACCEPTED(60010, "약관에 동의해야 가입할 수 있습니다.", HttpStatus.BAD_REQUEST),

    ;

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    @Override public boolean getSuccess() { return false; }
    @Override public int getCode() { return code; }
    @Override public String getMessage() { return message; }
    @Override public HttpStatus getHttpStatus() { return httpStatus; }

    public static UserExceptionResponseStatus fromMessage(String message) {
        for (UserExceptionResponseStatus status : values()) {
            if (status.name().equals(message)) {
                return status;
            }
        }
        return UserExceptionResponseStatus.REQUIRED_FIELD_MISSING; // fallback
    }
}
