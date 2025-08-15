package com.gonggumoa.domain.user.exception.handler;

import com.gonggumoa.domain.user.exception.*;
import com.gonggumoa.global.exception.RequiredFieldMissingException;
import com.gonggumoa.global.response.BaseErrorResponse;
import com.gonggumoa.global.response.status.BaseExceptionResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import static com.gonggumoa.global.response.status.BaseExceptionResponseStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackages = "com.gonggumoa.domain.user")
public class UserControllerAdvice {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(new BaseErrorResponse(EMAIL_ALREADY_EXISTS));
    }

    @ExceptionHandler(InvalidBirthdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseErrorResponse> handleInvalidBirthdateException(InvalidBirthdateException e) {
        return ResponseEntity.badRequest().body(new BaseErrorResponse(INVALID_BIRTHDATE));
    }

    @ExceptionHandler(PasswordsNotMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseErrorResponse> handlePasswordsNotMatchException(PasswordsNotMatchException e) {
        return ResponseEntity.badRequest().body(new BaseErrorResponse(PASSWORDS_NOT_MATCH));
    }

    @ExceptionHandler(PhoneAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseErrorResponse> handlePhoneAlreadyExistsException(PhoneAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(new BaseErrorResponse(PHONE_ALREADY_EXISTS));
    }

    @ExceptionHandler(NicknameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseErrorResponse> handleNicknameAlreadyExistsException(NicknameAlreadyExistsException e) {
        return ResponseEntity.badRequest().body(new BaseErrorResponse(NICKNAME_ALREADY_EXISTS));
    }

    @ExceptionHandler(EmailCodeSendFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseErrorResponse> handleEmailCodeSendFailedException(EmailCodeSendFailedException e) {
        return ResponseEntity.badRequest().body(new BaseErrorResponse(EMAIL_CODE_SEND_FAILED));
    }

    @ExceptionHandler(EmailCodeExpiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseErrorResponse> handleEmailCodeExpiredException(EmailCodeExpiredException e) {
        return ResponseEntity.badRequest().body(new BaseErrorResponse(EMAIL_CODE_EXPIRED));
    }

    @ExceptionHandler(EmailCodeNotMatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseErrorResponse> handleEmailCodeNotMatchException(EmailCodeNotMatchException e) {
        return ResponseEntity.badRequest().body(new BaseErrorResponse(EMAIL_CODE_NOT_MATCH));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.badRequest().body(new BaseErrorResponse(USER_NOT_FOUND));
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseErrorResponse> handleInvalidRefreshTokenException(InvalidRefreshTokenException e) {
        return ResponseEntity.badRequest().body(new BaseErrorResponse(INVALID_REFRESH_TOKEN));
    }


}
