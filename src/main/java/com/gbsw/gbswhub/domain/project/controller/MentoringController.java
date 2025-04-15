package com.gbsw.gbswhub.domain.project.controller;

import com.gbsw.gbswhub.domain.project.Service.MentoringService;
import com.gbsw.gbswhub.domain.project.db.CreateMentoringDto;
import com.gbsw.gbswhub.domain.user.model.User;
import com.gbsw.gbswhub.domain.user.service.UserService;
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

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mentoring")
@Tag(name = "Mentoring", description = "Mentoring에 관한 API")
public class MentoringController {

    private final MentoringService mentoringService;
    private final UserService userService;

    @PostMapping()
    @Operation(summary = "멘토멘티 생성", description = "멘토멘티 모집을 생성합니다.")
    @ApiResponse(responseCode = "200", ref = "#/components/responses/Mentoring200")
    @ApiResponse(responseCode = "400", ref = "#/components/responses/400")
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/404")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/Category404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<Map<String, String>> createMentoring(
            @Valid @RequestBody CreateMentoringDto createMentoringDto,
            Principal principal
    ) {
        User user = userService.getUser(principal.getName());
        return ResponseEntity.ok(mentoringService.createMentoring(createMentoringDto, user));
    }
}
