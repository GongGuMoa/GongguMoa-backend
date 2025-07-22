package com.gonggumoa.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gonggumoa.global.response.status.ResponseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import static com.gonggumoa.global.response.status.BaseExceptionResponseStatus.SUCCESS;

@Getter
@JsonPropertyOrder({"code", "message", "data"})
@Schema(description = "성공 응답")
public class BaseResponse<T> implements ResponseStatus {

    @Schema(description = "응답 코드", example = "20000")
    private final int code;
    @Schema(description = "응답 메시지", example = "요청에 성공했습니다.")
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "응답 데이터")
    private final T data;

    public BaseResponse(T data) {
        this.code = SUCCESS.getCode();
        this.message = SUCCESS.getMessage();
        this.data = data;
    }

    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<>(data);
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
