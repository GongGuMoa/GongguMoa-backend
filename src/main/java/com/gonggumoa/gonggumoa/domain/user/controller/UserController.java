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
    public BaseResponse<PostUserSignUpResponse> login(@Valid @RequestBody PostUserSignUpRequest request) {
        return BaseResponse.ok(userService.signUp(request));
    }

}
