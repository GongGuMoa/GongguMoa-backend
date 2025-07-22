package com.gonggumoa.domain.user.service;

import com.gonggumoa.domain.user.domain.User;
import com.gonggumoa.domain.user.dto.request.PostUserCheckEmailCodeRequest;
import com.gonggumoa.domain.user.dto.request.PostUserSendEmailCodeRequest;
import com.gonggumoa.domain.user.dto.request.PostUserSignUpRequest;
import com.gonggumoa.domain.user.dto.response.PostUserSignUpResponse;
import com.gonggumoa.domain.user.exception.*;
import com.gonggumoa.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.gonggumoa.global.response.status.BaseExceptionResponseStatus.*;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final RedisTemplate<String, String> redisTemplate;

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

    public void validateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new NicknameAlreadyExistsException(NICKNAME_ALREADY_EXISTS);
        }
    }

    public PostUserSignUpResponse signUp(PostUserSignUpRequest req) {
        if (!req.password().equals(req.passwordConfirm())) {
            throw new PasswordsNotMatchException(PASSWORDS_NOT_MATCH);
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

    public void sendEmailVerificationCode(PostUserSendEmailCodeRequest req) {
        String code = String.valueOf((int)(Math.random() * 900000) + 100000);
        redisTemplate.opsForValue().set(req.email(), code, Duration.ofMinutes(3));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(req.email());
        message.setSubject("공구모아 이메일 인증코드");
        message.setText("인증코드: " + code + "\n유효시간은 3분입니다.");
        try {
            mailSender.send(message);
        } catch (MailException e) {
            throw new EmailCodeSendFailedException(EMAIL_CODE_SEND_FAILED);
        }
    }

    public void checkEmailVerificationCode(PostUserCheckEmailCodeRequest req) {
        String storedCode = redisTemplate.opsForValue().get(req.email());

        if (storedCode == null) {
            throw new EmailCodeExpiredException(EMAIL_CODE_EXPIRED);
        }

        if (!storedCode.equals(req.code())) {
            throw new EmailCodeNotMatchException(EMAIL_CODE_NOT_MATCH);
        }

        redisTemplate.delete(req.email());
        redisTemplate.opsForValue().set("verified:" + req.email(), "true");
    }

}
