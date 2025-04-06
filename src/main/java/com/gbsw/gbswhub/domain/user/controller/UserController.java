package com.gbsw.gbswhub.domain.user.controller;

import com.gbsw.gbswhub.domain.user.db.CreateUserDto;
import com.gbsw.gbswhub.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "User", description = "User에 관한 API")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "새로운 사용자를 생성합니다.")
    @ApiResponse(responseCode = "200", ref = "#/components/responses/200")
    @ApiResponse(responseCode = "400", ref = "#/components/responses/400")
    @ApiResponse(responseCode = "409", ref = "#/components/responses/409")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<Map<String, String>> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.ok(userService.createUser(createUserDto));
    }
}
