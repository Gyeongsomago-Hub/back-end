package com.gbsw.gbswhub.domain.project.controller;


import com.gbsw.gbswhub.domain.project.Service.ProjectService;
import com.gbsw.gbswhub.domain.project.db.CreateProjectDto;
import com.gbsw.gbswhub.domain.project.db.ProjectDto;
import com.gbsw.gbswhub.domain.project.db.UpdateProjectDto;
import com.gbsw.gbswhub.domain.user.model.User;
import com.gbsw.gbswhub.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
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
    @ApiResponse(responseCode = "404", ref = "#/components/responses/404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<Map<String, String>> createProject(
            @Valid @RequestBody CreateProjectDto createProjectDto,
            Principal principal){

        User user = userService.getUser(principal.getName());

        return ResponseEntity.ok(projectService.createProject(createProjectDto, user));
    }


    @GetMapping()
    @Operation(summary = "프로젝트 전체 조회", description = "프로젝트 모집을 전체 조회합니다.")
    @ApiResponse(responseCode = "200", description = "프로젝트 모집 목록 조회 성공",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProjectDto.class))))
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<List<ProjectDto>> getAllProject() {
        return ResponseEntity.ok(projectService.getAllProject());
    }

    @GetMapping("/{id}")
    @Operation(summary = "프로젝트 하나 조회", description = "프로젝트 모집을 하나 조회합니다.")
    @ApiResponse(responseCode = "200", description = "프로젝트 모집 조회 성공",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProjectDto.class)))
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/Project404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id){
        ProjectDto project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "프로젝트 수정", description = "프로젝트 모집을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "프로젝트 모집 수정 성공",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProjectDto.class)))
    @ApiResponse(responseCode = "400", ref = "#/components/responses/400")
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "403", ref = "#/components/responses/403")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/Project404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<ProjectDto> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProjectDto dto,
            Principal principal){

        User user = userService.getUser(principal.getName());

        ProjectDto project = projectService.UpdateProject(id, dto, user);
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "프로젝트 삭제", description = "프로젝트 모집을 삭제합니다.")
    @ApiResponse(responseCode = "204", ref = "#/components/responses/204")
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "403", ref = "#/components/responses/403")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/Project404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id, Principal principal){

        User user = userService.getUser(principal.getName());

        projectService.deleteProject(id, user);
        return ResponseEntity.noContent().build();
    }
}
