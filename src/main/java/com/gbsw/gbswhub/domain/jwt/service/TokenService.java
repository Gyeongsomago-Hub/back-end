package com.gbsw.gbswhub.domain.jwt.service;

import com.gbsw.gbswhub.domain.global.Exception.BadRequestException;
import com.gbsw.gbswhub.domain.jwt.db.AccessTokenRequest;
import com.gbsw.gbswhub.domain.jwt.db.AccessTokenResponse;
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

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public AccessTokenResponse getAccessToken(AccessTokenRequest request) {
        User user = userService.getUser(request.getUsername());
        if (user != null) {
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return createAccessToken(user, null);
            }
        }
        return null;
    }

    private AccessTokenResponse createAccessToken(User user, String refreshToken) {
        Duration tokenDuration = Duration.ofDays(jwtProperties.getDuration());
        Duration refreshDutation = Duration.ofDays(jwtProperties.getRefreshDuration());

        RefreshToken savedRefreshToken = refreshTokenRepository.findByUsername(user.getUsername()).orElse(null);

        if (savedRefreshToken != null && refreshToken != null) {
            if (!savedRefreshToken.getRefreshToken().equals(refreshToken))
                return new AccessTokenResponse("Invalid token", null, null);
        }
        String accessToken = tokenProvider.generateToken(user, tokenDuration, true);
        String newRefreshToken = tokenProvider.generateToken(user, refreshDutation, false);

        if (savedRefreshToken == null)
            savedRefreshToken = new RefreshToken(user.getUsername(), newRefreshToken);
        else
            savedRefreshToken.setRefreshToken(newRefreshToken);
        refreshTokenRepository.save(savedRefreshToken);
        return new AccessTokenResponse("ok", accessToken, newRefreshToken);
    }

    public AccessTokenResponse refreshAccessToken(CreateAccessTokenByRefreshToken request) {
        try{
            Claims claims = tokenProvider.getClaims(request.getRefreshToken());
            String type = claims.get("type").toString();
            if(type == null || !type.equals("R")) {
                throw new Exception("Invalid token");
            }
            User user = userService.getUser(claims.getSubject());
            return createAccessToken(user, request.getRefreshToken());
        } catch (ExpiredJwtException e) {
            return new AccessTokenResponse("만료된 토큰", null, null);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new AccessTokenResponse(e.getMessage(), null, null);
        }
    }
}