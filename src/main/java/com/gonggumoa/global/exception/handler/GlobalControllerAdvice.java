package com.gonggumoa.global.exception.handler;

import com.gonggumoa.global.exception.BadRequestException;
import com.gonggumoa.global.exception.RequiredFieldMissingException;
import com.gonggumoa.global.exception.UnauthenticatedException;
import com.gonggumoa.global.response.BaseErrorResponse;
import com.gonggumoa.global.response.status.BaseExceptionResponseStatus;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Set;

import static com.gonggumoa.global.response.status.BaseExceptionResponseStatus.*;


@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    // 잘못된 요청일 경우
    @ExceptionHandler({BadRequestException.class, TypeMismatchException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<BaseErrorResponse> handle_BadRequest(Exception e){
        log.error("[handle_BadRequest]", e);
        return ResponseEntity.badRequest().body(new BaseErrorResponse(BAD_REQUEST));
    }

    // 요청한 api가 없을 경우
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<BaseErrorResponse> handle_NoHandlerFoundException(Exception e){
        log.error("[handle_NoHandlerFoundException]", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseErrorResponse(NOT_FOUND));
    }

    // 런타임 오류가 발생한 경우
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseErrorResponse> handle_RuntimeException(Exception e) {
        log.error("[handle_RuntimeException]", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseErrorResponse(INTERNAL_SERVER_ERROR));
    }

    // RequestParam, PathVariable 등의 validation 실패 (예: @RequestParam 제약 위반)
    @ExceptionHandler({ConstraintViolationException.class, RequiredFieldMissingException.class})
    public ResponseEntity<BaseErrorResponse> handleConstraintViolation(Exception e) {
        return ResponseEntity.badRequest().body(new BaseErrorResponse(REQUIRED_FIELD_MISSING));
    }

    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseEntity<BaseErrorResponse> handleUnauthenticatedException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BaseErrorResponse(AUTH_UNAUTHENTICATED));
    }

    // DTO validation 실패 (예: @NotBlank, @NotNull 등)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        FieldError error = ex.getBindingResult().getFieldErrors().get(0);
        String field = error.getField();
        String reason = error.getCode();  // 예: NotNull, NotBlank, Pattern, Email 등
        log.error("Validation error - field: {}, reason: {}", field, reason);

        if (isUserField(field) && isFormatError(reason)) {
            return ResponseEntity.badRequest().body(new BaseErrorResponse(mapUserFieldToError(field)));
        }

        return ResponseEntity.badRequest().body(new BaseErrorResponse(REQUIRED_FIELD_MISSING));
    }

    private boolean isUserField(String field) {
        return Set.of("email", "password", "nickname", "phoneNumber", "birthdate").contains(field);
    }

    private boolean isFormatError(String reason) {
        return Set.of("Pattern", "Email").contains(reason);
    }

    private BaseExceptionResponseStatus mapUserFieldToError(String field) {
        return switch (field) {
            case "email" -> INVALID_EMAIL_FORMAT;
            case "password" -> INVALID_PASSWORD_FORMAT;
            case "nickname" -> INVALID_NICKNAME_FORMAT;
            case "phoneNumber" -> INVALID_PHONE_NUMBER_FORMAT;
            case "birthdate" -> INVALID_BIRTHDATE_FORMAT;
            default -> REQUIRED_FIELD_MISSING;
        };
    }




}
