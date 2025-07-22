package com.gonggumoa.global.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gonggumoa.global.response.status.ResponseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;



@Getter
@JsonPropertyOrder({"code", "message","data"})
@Schema(description = "에러 응답")
public class BaseErrorResponse implements ResponseStatus {

    @Schema(description = "에러 코드", example = "40000")
    private final int code;
    @Schema(description = "에러 메시지", example = "유효하지 않은 요청입니다.")
    private final String message;
    @Schema(description = "응답 데이터")
    private final Object data;

    public BaseErrorResponse(ResponseStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.data = null;
    }

    public BaseErrorResponse(ResponseStatus status, String message) {
        this.code = status.getCode();
        this.message = message;
        this.data = null;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
