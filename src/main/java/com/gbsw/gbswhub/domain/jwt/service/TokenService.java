package com.gbsw.gbswhub.domain.jwt.service;

import com.gbsw.gbswhub.domain.global.Exception.BadRequestException;
import com.gbsw.gbswhub.domain.jwt.db.AccessTokenRequest;
import com.gbsw.gbswhub.domain.jwt.properties.JwtProperties;
import com.gbsw.gbswhub.domain.jwt.provider.TokenProvider;
import com.gbsw.gbswhub.domain.user.model.User;
import com.gbsw.gbswhub.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final JwtProperties jwtProperties;
    private final UserService userService;

    public String getAccessToken(AccessTokenRequest request) {
        User user = userService.getUser(request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("비밀번호가 올바르지 않습니다.");
        }
        // 토큰 생성
        return tokenProvider.generateToken(user, Duration.ofDays(jwtProperties.getDuration()));
    }
}