package com.gbsw.gbswhub.domain.jwt.service;

import com.gbsw.gbswhub.domain.global.Error.ErrorCode;
import com.gbsw.gbswhub.domain.global.Exception.BusinessException;
import com.gbsw.gbswhub.domain.jwt.db.AccessTokenRequest;
import com.gbsw.gbswhub.domain.jwt.db.CreateAccessTokenByRefreshToken;
import com.gbsw.gbswhub.domain.jwt.db.RefreshTokenRepository;
import com.gbsw.gbswhub.domain.jwt.model.RefreshToken;
import com.gbsw.gbswhub.domain.jwt.properties.JwtProperties;
import com.gbsw.gbswhub.domain.jwt.provider.TokenProvider;
import com.gbsw.gbswhub.domain.user.model.User;
import com.gbsw.gbswhub.domain.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public Map<String, String> getAccessToken(AccessTokenRequest request) {
        User user = userService.getUser(request.getUsername());

        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }

        return createAccessToken(user, null);
    }


    private Map<String, String> createAccessToken(User user, String refreshToken) {
        Duration tokenDuration = Duration.ofDays(jwtProperties.getDuration());
        Duration refreshDutation = Duration.ofDays(jwtProperties.getRefreshDuration());

        RefreshToken savedRefreshToken = refreshTokenRepository.findByUsername(user.getUsername()).orElse(null);

        if (savedRefreshToken != null && refreshToken != null) {
            if (!savedRefreshToken.getRefreshToken().equals(refreshToken))
               throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }
        String accessToken = tokenProvider.generateToken(user, tokenDuration, true);
        String newRefreshToken = tokenProvider.generateToken(user, refreshDutation, false);

        if (savedRefreshToken == null) {
            savedRefreshToken = new RefreshToken(user.getUsername(), newRefreshToken);
        } else {
            savedRefreshToken.setRefreshToken(newRefreshToken);
        }

        refreshTokenRepository.save(savedRefreshToken);

        Map<String, String> response = new HashMap<>();
        response.put("message", "토큰이 발급되었습니다.");
        response.put("accessToken", accessToken);
        response.put("refreshToken", newRefreshToken);

        return response;
    }

    public Map<String, String> refreshAccessToken(CreateAccessTokenByRefreshToken request) {
        Claims claims;
        try {
            claims = tokenProvider.getClaims(request.getRefreshToken());
        } catch (ExpiredJwtException e) {
            throw new BusinessException(ErrorCode.EXPIRED_TOKEN);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }

        String type = claims.get("type", String.class);
        if (type == null || !type.equals("R")) {
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }

        User user = userService.getUser(claims.getSubject());

        return createAccessToken(user, request.getRefreshToken());
    }
}