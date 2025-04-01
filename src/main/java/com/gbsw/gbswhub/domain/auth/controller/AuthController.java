package com.gbsw.gbswhub.domain.auth.controller;

import com.gbsw.gbswhub.domain.auth.db.LoginRequest;
import com.gbsw.gbswhub.domain.auth.db.LoginResponse;
import com.gbsw.gbswhub.domain.auth.service.AuthService;
import com.gbsw.gbswhub.domain.user.filter.DataNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        try {
            boolean isValid = authService.validateUser(request);
            LoginResponse response = new LoginResponse(isValid ? "success" : "fail");
            return ResponseEntity.ok(response);
        } catch (DataNotFoundException e) {
            return ResponseEntity.status(404).body(new LoginResponse("사용자가 존재하지 않습니다."));
        }
    }
}