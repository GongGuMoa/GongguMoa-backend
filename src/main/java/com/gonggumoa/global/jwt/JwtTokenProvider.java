package com.gonggumoa.global.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;

    private Key key;

    private final long ACCESS_TOKEN_EXPIRE = 1000L * 60 * 60 * 24; // 액세스 토큰 만료시간 = 1일
    private final long REFRESH_TOKEN_EXPIRE = 1000L * 60 * 60 * 24 * 7; // 리프레시 토큰 만료시간 = 7일


    @PostConstruct
    protected void init() {
        byte[] keyBytes = Base64.getEncoder().encode(secretKey.getBytes());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(Long userId) {
        return createToken(userId, ACCESS_TOKEN_EXPIRE);
    }

    public String createRefreshToken(Long userId) {
        return createToken(userId, REFRESH_TOKEN_EXPIRE);
    }

    private String createToken(Long userId, long expireTime) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expireTime);

        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long getUserId(String token) {
        return Long.parseLong(
                Jwts.parserBuilder().setSigningKey(key).build()
                        .parseClaimsJws(token)
                        .getBody().getSubject()
        );
    }

    public long getRefreshTokenExpireMillis() {
        return REFRESH_TOKEN_EXPIRE;
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
