package com.gbsw.gbswhub.domain.auth.controller;

import com.gbsw.gbswhub.domain.global.Exception.BadRequestException;
import com.gbsw.gbswhub.domain.global.Exception.DataNotFoundException;
import com.gbsw.gbswhub.domain.jwt.db.AccessTokenRequest;
import com.gbsw.gbswhub.domain.jwt.db.AccessTokenResponse;
import com.gbsw.gbswhub.domain.jwt.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
            String token = tokenService.getAccessToken(request);
            return ResponseEntity.ok(new AccessTokenResponse("ok", token, null));
        } catch (BadRequestException e) {
            return ResponseEntity.status(400).body(new AccessTokenResponse(e.getMessage()));
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(400).body(new AccessTokenResponse("아이디가 올바르지 않습니다."));
        }
    }
}