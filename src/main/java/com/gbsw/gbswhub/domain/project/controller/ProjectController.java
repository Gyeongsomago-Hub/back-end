package com.gbsw.gbswhub.domain.project.controller;


import com.gbsw.gbswhub.domain.project.Service.ProjectService;
import com.gbsw.gbswhub.domain.project.db.CreateMentoringDto;
import com.gbsw.gbswhub.domain.project.db.CreateProjectDto;
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
@RequestMapping("/api/project")
@Tag(name = "Project", description = "Projcet에 관한 API")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "프로젝트 생성", description = "프로젝트 모집을 생성합니다.")
    @ApiResponse(responseCode = "200", ref = "#/components/responses/Project200")
    @ApiResponse(responseCode = "400", ref = "#/components/responses/400")
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "403", ref = "#/components/responses/403")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/404")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/Category404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<Map<String, String>> createProject(
            @Valid @RequestBody CreateProjectDto createProjectDto,
            Principal principal){

        User user = userService.getUser(principal.getName());

        return ResponseEntity.ok(projectService.createProject(createProjectDto, user));
    }

    @PostMapping("/mentor")
    @Operation(summary = "멘토멘티 생성", description = "멘토멘티 모집을 생성합니다.")
    @ApiResponse(responseCode = "200", ref = "#/components/responses/Mentoring200")
    @ApiResponse(responseCode = "400", ref = "#/components/responses/400")
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "403", ref = "#/components/responses/403")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<Map<String, String>> createMentoring(
            @Valid @RequestBody CreateMentoringDto createMentoringDto,
            Principal principal
    ) {
        User user = userService.getUser(principal.getName());
        return ResponseEntity.ok(projectService.createMentoring(createMentoringDto, user));
    }
}
