package com.gbsw.gbswhub.domain.ai.model.controller;

import com.gbsw.gbswhub.domain.ai.model.db.RequestRoadMapDto;
import com.gbsw.gbswhub.domain.ai.model.service.ChatGptService;
import com.gbsw.gbswhub.domain.user.model.User;
import com.gbsw.gbswhub.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ai")
@Tag(name = "Ai", description = "Ai에 관한 API")
public class ChatGptController {

    private final ChatGptService chatGptService;
    private final UserService userService;


    @PostMapping()
    @Operation(summary = "ai 응답 생성", description = "ai 응답을 생성합니다.")
    @ApiResponse(responseCode = "200", ref = "#/components/responses/400")
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "403", ref = "#/components/responses/403")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<Map<String, String>> generateRoadMap(@Valid @RequestBody RequestRoadMapDto dto,
                                                               Principal principal) {
        User user = userService.getUser(principal.getName());

        return ResponseEntity.ok(chatGptService.generateRoadmap(dto, user).getBody());
    }
}
