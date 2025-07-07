package com.gonggumoa.gonggumoa.domain.user.dto.request;

import jakarta.validation.constraints.*;
public record PostUserSignUpRequest(
        @NotBlank
        String loginId,

        @NotBlank
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+{}|:;<>?~]).{8,16}$",
                message = "INVALID_PASSWORD_FORMAT"
        )
        String password,

        @NotBlank
        @Email(message = "INVALID_EMAIL_FORMAT")
        String email,

        @NotBlank
        String name,

        @NotBlank
        String nickname,

        @NotBlank
        @Pattern(regexp = "^\\d{10,11}$", message = "INVALID_PHONE_NUMBER_FORMAT")
        String phoneNumber,

        @NotBlank
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "INVALID_BIRTHDATE_FORMAT")
        String birthdate,

        @AssertTrue(message = "AGREEMENT_NOT_ACCEPTED")
        boolean agreementVerified
) {}
