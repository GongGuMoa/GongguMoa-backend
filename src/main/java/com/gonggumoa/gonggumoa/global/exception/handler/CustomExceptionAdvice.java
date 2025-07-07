package com.gonggumoa.gonggumoa.global.exception.handler;

import com.gonggumoa.gonggumoa.global.exception.CustomException;
import com.gonggumoa.gonggumoa.global.response.BaseErrorResponse;

import com.gonggumoa.gonggumoa.global.response.status.UserExceptionResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionAdvice {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseErrorResponse> handleCustom(CustomException e) {
        return ResponseEntity.status(e.getStatus().getHttpStatus())
                .body(new BaseErrorResponse(e.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 이건 그냥 고정으로 써도 괜찮음
    public BaseErrorResponse handleValidationException(MethodArgumentNotValidException e) {
        String messageKey = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        UserExceptionResponseStatus status = UserExceptionResponseStatus.fromMessage(messageKey);

        return new BaseErrorResponse(status);
    }

}
