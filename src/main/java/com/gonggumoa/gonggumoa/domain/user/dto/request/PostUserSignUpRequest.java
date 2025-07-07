package com.gonggumoa.gonggumoa.domain.user.dto.request;

import jakarta.validation.constraints.*;
public record PostUserSignUpRequest(
        @NotBlank
        @Email(message = "INVALID_EMAIL_FORMAT")
        String email,

        @NotBlank
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+{}|:;<>?~]).{8,16}$",
                message = "INVALID_PASSWORD_FORMAT"
        )
        String password,

        @NotBlank(message = "REQUIRED_FIELD_MISSING")
        String passwordConfirm,

        @NotBlank
        String name,

        @NotBlank
        @Pattern(
                regexp = "^(?=.*[a-zA-Z가-힣])[a-zA-Z가-힣0-9]{2,8}$",
                message = "INVALID_NICKNAME_FORMAT"
        )
        String nickname,

        @NotBlank
        @Pattern(regexp = "^\\d{10,11}$", message = "INVALID_PHONE_NUMBER_FORMAT")
        String phoneNumber,

        @NotBlank
        @Pattern(regexp = "^\\d{8}", message = "INVALID_BIRTHDATE_FORMAT")
        String birthdate

) {}
