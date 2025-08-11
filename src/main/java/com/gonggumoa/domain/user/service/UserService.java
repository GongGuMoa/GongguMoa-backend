package com.gonggumoa.domain.user.service;

import com.gonggumoa.domain.user.entity.User;
import com.gonggumoa.domain.user.dto.request.*;
import com.gonggumoa.domain.user.dto.response.*;
import com.gonggumoa.domain.user.exception.*;
import com.gonggumoa.domain.user.repository.UserRepository;

import com.gonggumoa.global.context.UserContext;
import com.gonggumoa.global.exception.RequiredFieldMissingException;
import com.gonggumoa.global.security.jwt.JwtTokenProvider;
import com.gonggumoa.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.gonggumoa.global.response.status.BaseExceptionResponseStatus.*;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtTokenProvider tokenProvider;
    private final RedisService redisService;


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
    
    @Transactional
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

    public PostUserLoginResponse login(PostUserLoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmailOrPhone(request.identifier());

        // 액세스 토큰 만료됐을때 리프레시 토큰으로 로그인
        if (userOpt.isEmpty() && tokenProvider.validateToken(request.identifier())) {
            Long userId = tokenProvider.getUserId(request.identifier());
            String stored = redisService.getRefreshToken(userId);

            if (!request.identifier().equals(stored)) {
                throw new InvalidRefreshTokenException(INVALID_REFRESH_TOKEN);
            }

            String newAccessToken = tokenProvider.createAccessToken(userId);
            return new PostUserLoginResponse(userId, newAccessToken, request.identifier());
        }

        // 일반 로그인
        if (request.password() == null || request.password().isBlank()) {
            throw new RequiredFieldMissingException(REQUIRED_FIELD_MISSING);
        }

        User user = userOpt
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }

        String accessToken = tokenProvider.createAccessToken(user.getUserId());
        String refreshToken = tokenProvider.createRefreshToken(user.getUserId());

        redisService.saveRefreshToken(user.getUserId(), refreshToken, tokenProvider.getRefreshTokenExpireMillis());

        return new PostUserLoginResponse(user.getUserId(), accessToken, refreshToken);
    }


    @Transactional
    public void setLocation(PostUserSetLocationRequest request) {
        User user = UserContext.getUser();
        user.updateLocation(request.latitude(), request.longitude(), request.location());
        userRepository.save(user);
    }
}
