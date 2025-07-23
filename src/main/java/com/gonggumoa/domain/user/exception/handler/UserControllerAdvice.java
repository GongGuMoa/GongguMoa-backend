package com.gonggumoa.domain.user.exception.handler;

import com.gonggumoa.domain.user.exception.*;
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

import static com.gonggumoa.global.response.status.BaseExceptionResponseStatus.*;

@RestControllerAdvice(basePackages = "com.gonggumoa.domain.user")
public class UserControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        FieldError error = bindingResult.getFieldErrors().get(0);

        String field = error.getField();
        String reason = error.getCode();  // ex) NotBlank, Pattern, Email 등

        BaseExceptionResponseStatus status = switch (reason) {
            case "Pattern", "Email" -> mapFieldToInvalidFormatStatus(field);
            default -> REQUIRED_FIELD_MISSING;
        };

        return ResponseEntity.badRequest().body(new BaseErrorResponse(status));
    }

    private BaseExceptionResponseStatus mapFieldToInvalidFormatStatus(String field) {
        return switch (field) {
            case "email" -> INVALID_EMAIL_FORMAT;
            case "password" -> INVALID_PASSWORD_FORMAT;
            case "nickname" -> INVALID_NICKNAME_FORMAT;
            case "phoneNumber" -> INVALID_PHONE_NUMBER_FORMAT;
            case "birthdate" -> INVALID_BIRTHDATE_FORMAT;
            default -> REQUIRED_FIELD_MISSING;
        };
    }

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

    @ExceptionHandler(RequiredFieldMissingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseErrorResponse> handleRequiredFieldMissingException(RequiredFieldMissingException e) {
        return ResponseEntity.badRequest().body(new BaseErrorResponse(REQUIRED_FIELD_MISSING));
    }


}
