package com.gonggumoa.domain.user.service;

import com.gonggumoa.domain.user.domain.User;
import com.gonggumoa.domain.user.dto.request.PostUserSignUpRequest;
import com.gonggumoa.domain.user.dto.response.PostUserSignUpResponse;
import com.gonggumoa.domain.user.exception.*;
import com.gonggumoa.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.gonggumoa.global.response.status.BaseExceptionResponseStatus.*;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(EMAIL_ALREADY_EXISTS);
        }
    }

    public void validatePhoneNumber(String phoneNumber) {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new PhoneAlreadyExistsException(PHONE_ALREADY_EXISTS);
        }
    }

    public PostUserSignUpResponse signUp(PostUserSignUpRequest req) {
        if (!req.password().equals(req.passwordConfirm())) {
            throw new PasswordsNotMatchException(PASSWORDS_NOT_MATCH);
        }

        if (userRepository.existsByNickname(req.nickname())) {
            throw new NicknameAlreadyExistsException(NICKNAME_ALREADY_EXISTS);
        }

        LocalDate birthdate;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        birthdate = LocalDate.parse(req.birthdate(), formatter);
        if (birthdate.isAfter(LocalDate.now())) {
            throw new InvalidBirthdateException(INVALID_BIRTHDATE);
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

        return PostUserSignUpResponse.of(user.getUserId());
    }

}
