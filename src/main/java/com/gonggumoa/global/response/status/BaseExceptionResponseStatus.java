package com.gonggumoa.global.response.status;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BaseExceptionResponseStatus implements ResponseStatus {
    SUCCESS(20000, "요청에 성공했습니다."),
    BAD_REQUEST(40000, "유효하지 않은 요청입니다."),
    NOT_FOUND(40400, "존재하지 않는 API입니다."),
    INTERNAL_SERVER_ERROR(50000, "서버 내부 오류입니다."),


    //user 6000~
    REQUIRED_FIELD_MISSING(60000, "필수 입력 항목이 누락되었습니다."),
    EMAIL_ALREADY_EXISTS(60001, "이미 사용 중인 이메일입니다."),
    PHONE_ALREADY_EXISTS(60002, "이미 사용 중인 전화번호입니다."),
    NICKNAME_ALREADY_EXISTS(60003,"이미 사용 중인 닉네임입니다."),
    INVALID_PASSWORD_FORMAT(60004, "비밀번호 형식이 올바르지 않습니다."),
    INVALID_EMAIL_FORMAT(60005, "이메일 형식이 올바르지 않습니다."),
    INVALID_PHONE_NUMBER_FORMAT(60006, "전화번호 형식이 올바르지 않습니다."),
    INVALID_NICKNAME_FORMAT(60007, "닉네임 형식이 올바르지 않습니다."),
    INVALID_BIRTHDATE_FORMAT(60008, "생년월일 형식이 올바르지 않습니다."),
    INVALID_BIRTHDATE(60009, "유효한 생년월일이 아닙니다."),
    PASSWORDS_NOT_MATCH(60010, "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    EMAIL_CODE_SEND_FAILED(60011, "이메일 인증 코드 전송에 실패했습니다."),
    EMAIL_CODE_EXPIRED(60012, "인증코드가 만료되었거나 존재하지 않습니다."),
    EMAIL_CODE_NOT_MATCH(60013, "인증코드가 일치하지 않습니다."),

    ;


    private final int code;
    private final String message;


    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
