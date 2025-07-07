package com.gonggumoa.gonggumoa.domain.user.service;

import com.gonggumoa.gonggumoa.domain.user.domain.User;
import com.gonggumoa.gonggumoa.domain.user.dto.request.PostUserSignUpRequest;
import com.gonggumoa.gonggumoa.domain.user.dto.response.PostUserSignUpResponse;
import com.gonggumoa.gonggumoa.domain.user.repository.UserRepository;
import com.gonggumoa.gonggumoa.global.exception.CustomException;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.gonggumoa.gonggumoa.global.response.status.UserExceptionResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public PostUserSignUpResponse signUp(PostUserSignUpRequest req) {

        if (userRepository.existsByLoginId(req.loginId())) {
            throw new CustomException(USER_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(req.email())) {
            throw new CustomException(EMAIL_ALREADY_EXISTS);
        }
        if (userRepository.existsByPhoneNumber(req.phoneNumber())) {
            throw new CustomException(PHONENUM_ALREADY_EXISTS);
        }

        LocalDate birthdate;
        try {
            birthdate = LocalDate.parse(req.birthdate());
            if (birthdate.isAfter(LocalDate.now())) {
                throw new CustomException(INVALID_BIRTHDATE);
            }
        } catch (Exception e) {
            throw new CustomException(INVALID_BIRTHDATE_FORMAT);
        }

        User user = User.builder()
                .loginId(req.loginId())
                .password(passwordEncoder.encode(req.password()))
                .email(req.email())
                .name(req.name())
                .nickname(req.nickname())
                .phoneNumber(req.phoneNumber())
                .birthdate(birthdate)
                .build();

        userRepository.save(user);

        return PostUserSignUpResponse.of(user.getId());
    }

}
