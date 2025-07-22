package com.gonggumoa.domain.user.controller;

import com.gonggumoa.domain.user.dto.request.PostUserSignUpRequest;
import com.gonggumoa.domain.user.dto.response.PostUserSignUpResponse;
import com.gonggumoa.domain.user.service.UserService;
import com.gonggumoa.global.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입", description = "새로운 회원을 생성합니다.")
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<PostUserSignUpResponse>> Signup(
            @Valid @RequestBody PostUserSignUpRequest request) {
        return ResponseEntity.ok(BaseResponse.ok(userService.signUp(request)));
    }

    @Operation(summary = "이메일 중복 확인", description = "중복되는 이메일이 있는지 확인합니다.")
    @GetMapping("/check-email")
    public ResponseEntity<BaseResponse<Void>> checkEmailDuplicate(
            @RequestParam String email) {
        userService.validateEmail(email);
        return ResponseEntity.ok(BaseResponse.ok(null));
    }

    @Operation(summary = "전화번호 중복 확인", description = "중복되는 전화번호가 있는지 확인합니다.")
    @GetMapping("/check-phone")
    public ResponseEntity<BaseResponse<Void>> checkPhoneDuplicate(
            @RequestParam String phone) {
        userService.validatePhoneNumber(phone);
        return ResponseEntity.ok(BaseResponse.ok(null));
    }

}
