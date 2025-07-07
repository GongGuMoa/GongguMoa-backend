package com.gonggumoa.gonggumoa.domain.user.controller;

import com.gonggumoa.gonggumoa.domain.user.dto.request.PostUserSignUpRequest;
import com.gonggumoa.gonggumoa.domain.user.dto.response.PostUserSignUpResponse;
import com.gonggumoa.gonggumoa.domain.user.service.UserService;
import com.gonggumoa.gonggumoa.global.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public BaseResponse<PostUserSignUpResponse> Signup(@Valid @RequestBody PostUserSignUpRequest request) {
        return BaseResponse.ok(userService.signUp(request));
    }

    @GetMapping("/check-email")
    public BaseResponse<Void> checkEmailDuplicate(@RequestParam String email) {
        userService.validateEmail(email);
        return BaseResponse.ok(null);
    }

    @GetMapping("/check-phone")
    public BaseResponse<Void> checkPhoneDuplicate(@RequestParam String phone) {
        userService.validatePhoneNumber(phone);
        return BaseResponse.ok(null);
    }

}
