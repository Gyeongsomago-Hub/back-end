package com.gbsw.gbswhub.domain.auth.controller;

import com.gbsw.gbswhub.domain.global.Exception.BadRequestException;
import com.gbsw.gbswhub.domain.global.Exception.DataNotFoundException;
import com.gbsw.gbswhub.domain.jwt.db.AccessTokenRequest;
import com.gbsw.gbswhub.domain.jwt.db.AccessTokenResponse;
import com.gbsw.gbswhub.domain.jwt.db.CreateAccessTokenByRefreshToken;
import com.gbsw.gbswhub.domain.jwt.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(
            @RequestBody @Valid AccessTokenRequest request, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder b = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                b.append(error.getDefaultMessage()).append(" ");
            }
            return ResponseEntity.badRequest().body(new AccessTokenResponse(b.toString()));
        }

        try {
            AccessTokenResponse response = tokenService.getAccessToken(request);
            if (response != null && response.getToken() != null) {
                String token = response.getToken();
                String refreshToken = response.getRefreshToken();
                return ResponseEntity.ok(new AccessTokenResponse("토큰이 생성되었습니다.", token, refreshToken));
            } else {
                return ResponseEntity.status(400).body(new AccessTokenResponse("비밀번호가 올바르지 않습니다."));
            }
        } catch (BadRequestException e) {
            return ResponseEntity.status(400).body(new AccessTokenResponse(e.getMessage()));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(400).body(new AccessTokenResponse("아이디가 올바르지 않습니다."));
        }
    }

    @PostMapping("/login/token")
    public ResponseEntity<AccessTokenResponse> tokenLogin(
            @RequestBody CreateAccessTokenByRefreshToken request
    ) {
        AccessTokenResponse response = tokenService.refreshAccessToken(request);
        if(response != null)
            return ResponseEntity.ok().body(response);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}