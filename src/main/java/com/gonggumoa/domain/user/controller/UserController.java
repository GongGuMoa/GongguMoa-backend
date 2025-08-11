package com.gonggumoa.domain.user.controller;

import com.gonggumoa.domain.user.dto.request.*;
import com.gonggumoa.domain.user.dto.response.*;
import com.gonggumoa.domain.user.service.UserService;
import com.gonggumoa.global.docs.DocumentedApiErrors;
import com.gonggumoa.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.gonggumoa.global.response.status.BaseExceptionResponseStatus.*;

@Validated
@Tag(name = "User", description = "UserController - 회원 관련 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입", description = "새로운 회원을 생성합니다.")
    @PostMapping("/signup")
    @DocumentedApiErrors({
            PASSWORDS_NOT_MATCH,
            NICKNAME_ALREADY_EXISTS,
            INVALID_BIRTHDATE,
    })
    public ResponseEntity<BaseResponse<PostUserSignUpResponse>> Signup(
            @Valid @RequestBody PostUserSignUpRequest request) {
        return ResponseEntity.ok(BaseResponse.ok(userService.signUp(request)));
    }


    @Operation(summary = "이메일 중복 확인", description = "중복되는 이메일이 있는지 확인합니다.")
    @GetMapping("/check-email")
    @DocumentedApiErrors({EMAIL_ALREADY_EXISTS})
    public ResponseEntity<BaseResponse<Void>> checkEmailDuplicate(
            @RequestParam String email) {
        userService.validateEmail(email);
        return ResponseEntity.ok(BaseResponse.ok(null));
    }

    @Operation(summary = "전화번호 중복 확인", description = "중복되는 전화번호가 있는지 확인합니다.")
    @GetMapping("/check-phone")
    @DocumentedApiErrors({PHONE_ALREADY_EXISTS})
    public ResponseEntity<BaseResponse<Void>> checkPhoneDuplicate(
            @RequestParam String phone) {
        userService.validatePhoneNumber(phone);
        return ResponseEntity.ok(BaseResponse.ok(null));
    }

    @Operation(summary = "닉네임 중복 확인", description = "중복되는 닉네임이 있는지 확인합니다.")
    @GetMapping("/check-nickname")
    @DocumentedApiErrors({NICKNAME_ALREADY_EXISTS})
    public ResponseEntity<BaseResponse<Void>> checkNicknameDuplicate(
            @RequestParam String nickname) {
        userService.validateNickname(nickname);
        return ResponseEntity.ok(BaseResponse.ok(null));
    }

    @Operation(summary = "이메일 인증코드 발송", description = "이메일에 인증 코드를 발송합니다.")
    @PostMapping("/email-code")
    @DocumentedApiErrors({EMAIL_CODE_SEND_FAILED})
    public ResponseEntity<BaseResponse<Void>> sendEmailVerificationCode (
            @Valid @RequestBody PostUserSendEmailCodeRequest request) {
        userService.sendEmailVerificationCode(request);
        return ResponseEntity.ok(BaseResponse.ok(null));
    }

    @Operation(summary = "이메일 인증코드 확인", description = "입력한 인증코드를 검증합니다.")
    @PostMapping("/check-emailcode")
    @DocumentedApiErrors({EMAIL_CODE_EXPIRED, EMAIL_CODE_NOT_MATCH})
    public ResponseEntity<BaseResponse<Void>> checkEmailCode(
            @RequestBody PostUserCheckEmailCodeRequest request) {
        userService.checkEmailVerificationCode(request);
        return ResponseEntity.ok(BaseResponse.ok(null));
    }

    @Operation(summary = "로그인", description = "이메일 또는 전화번호, 비밀번호로 로그인합니다.")
    @PostMapping("/login")
    @DocumentedApiErrors({INVALID_REFRESH_TOKEN, REQUIRED_FIELD_MISSING, USER_NOT_FOUND})
    public ResponseEntity<BaseResponse<PostUserLoginResponse>> login(
            @RequestBody @Valid PostUserLoginRequest request) {
        return ResponseEntity.ok(BaseResponse.ok(userService.login(request)));
    }


    @Operation(summary = "내 위치 설정", description = "사용자의 현재 위치 또는 선택한 주소를 내 위치로 저장합니다.")
    @PostMapping("/locations")
    public ResponseEntity<BaseResponse<Void>> setLocation(
            @RequestBody @Valid PostUserSetLocationRequest request
    ) {
        userService.setLocation(request);
        return ResponseEntity.ok(BaseResponse.ok(null));
    }

}
