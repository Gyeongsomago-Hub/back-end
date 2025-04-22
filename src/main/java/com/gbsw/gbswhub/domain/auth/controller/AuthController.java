package com.gbsw.gbswhub.domain.auth.controller;


import com.gbsw.gbswhub.domain.jwt.db.AccessTokenRequest;
import com.gbsw.gbswhub.domain.jwt.db.CreateAccessTokenByRefreshToken;
import com.gbsw.gbswhub.domain.jwt.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "인증에 관한 API")
public class AuthController {
    private final TokenService tokenService;

    @PostMapping("/login")
    @Operation(summary = "사용자 로그인", description = "사용자가 로그인 할 때 사용하는 API")
    @ApiResponse(responseCode = "400", ref = "#/components/responses/400")
    @ApiResponse(responseCode = "401", ref = "#/components/responses/401")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public  ResponseEntity<Map<String, String>> login(@Valid @RequestBody AccessTokenRequest accessTokenRequest){
        return ResponseEntity.ok(tokenService.getAccessToken(accessTokenRequest));
    }

    @PostMapping("/login/token")
    @Operation(summary = "토큰 재발급", description = "AccessToken 기한이 만료되었을때 RefreshToken을 이용해 AccessToken을 재발급 받는 API")
    @ApiResponse(responseCode = "400", ref = "#/components/responses/400")
    @ApiResponse(responseCode = "401", ref = "#/components/responses/401")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<Map<String, String>> tokenLogin(@Valid @RequestBody CreateAccessTokenByRefreshToken request) {
        return ResponseEntity.ok(tokenService.refreshAccessToken(request));
    }
}