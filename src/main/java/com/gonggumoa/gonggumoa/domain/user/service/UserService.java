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
import java.time.format.DateTimeFormatter;

import static com.gonggumoa.gonggumoa.global.response.status.UserExceptionResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new CustomException(EMAIL_ALREADY_EXISTS);
        }
    }

    public void validatePhoneNumber(String phoneNumber) {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new CustomException(PHONENUM_ALREADY_EXISTS);
        }
    }

    public PostUserSignUpResponse signUp(PostUserSignUpRequest req) {
        if (!req.password().equals(req.passwordConfirm())) {
            throw new CustomException(PASSWORDS_DO_NOT_MATCH);
        }

        LocalDate birthdate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            birthdate = LocalDate.parse(req.birthdate(), formatter);
            if (birthdate.isAfter(LocalDate.now())) {
                throw new CustomException(INVALID_BIRTHDATE);
            }
        } catch (Exception e) {
            throw new CustomException(INVALID_BIRTHDATE_FORMAT);
        }

        User user = User.builder()
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
