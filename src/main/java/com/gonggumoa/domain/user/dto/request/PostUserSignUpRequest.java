package com.gonggumoa.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
public record PostUserSignUpRequest(
        @Schema(description = "이메일", example = "test@email.com")
        @NotBlank
        @Email
        String email,

        @Schema(description = "비밀번호", example = "abc12345!")
        @NotBlank
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+{}|:;<>?~]).{8,16}$")
        String password,

        @Schema(description = "비밀번호 확인", example = "abc12345!")
        @NotBlank
        String passwordConfirm,

        @Schema(description = "이름", example = "양지윤")
        @NotBlank
        String name,

        @Schema(description = "닉네임", example = "모아모아")
        @NotBlank
        @Pattern(regexp = "^(?=.*[a-zA-Z가-힣])[a-zA-Z가-힣0-9]{2,8}$")
        String nickname,

        @Schema(description = "전화번호", example = "01012345678")
        @NotBlank
        @Pattern(regexp = "^\\d{10,11}$")
        String phoneNumber,

        @Schema(description = "생년월일", example = "20040101")
        @NotBlank
        @Pattern(regexp = "^\\d{8}$")
        String birthdate

) {}
