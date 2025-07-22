package com.gonggumoa.domain.user.dto.request;

import jakarta.validation.constraints.*;
public record PostUserSignUpRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+{}|:;<>?~]).{8,16}$")
        String password,

        @NotBlank
        String passwordConfirm,

        @NotBlank
        String name,

        @NotBlank
        @Pattern(regexp = "^(?=.*[a-zA-Z가-힣])[a-zA-Z가-힣0-9]{2,8}$")
        String nickname,

        @NotBlank
        @Pattern(regexp = "^\\d{10,11}$")
        String phoneNumber,

        @NotBlank
        @Pattern(regexp = "^\\d{8}$")
        String birthdate

) {}
