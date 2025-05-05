package com.gbsw.gbswhub.domain.participation.controller;

import com.gbsw.gbswhub.domain.participation.db.*;
import com.gbsw.gbswhub.domain.participation.service.ParticipationService;
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
@RequestMapping("/api/participation")
@Tag(name = "Participation", description = "participation 에 관한 API")
public class ParticipationController {
    private final ParticipationService participationService;
    private final UserService userService;

    @PostMapping("/project")
    @Operation(summary = "프로젝트 참가", description = "프로젝트를 참가합니다.")
    @ApiResponse(responseCode = "200", ref = "#/components/responses/RequestProject200")
    @ApiResponse(responseCode = "400", ref = "#/components/responses/400")
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/Project404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<Map<String, String>> RequestProject(@Valid @RequestBody RequestProjectDto dto, Principal principal){
        User user =  userService.getUser(principal.getName());

        return  ResponseEntity.ok(participationService.RequestProject(dto, user));
    }

    @PostMapping("/mentoring")
    @Operation(summary = "멘토멘티 참가 요청", description = "멘토멘티 참가 요청을 보냅니다.")
    @ApiResponse(responseCode = "200", ref = "#/components/responses/RequestMentoring200")
    @ApiResponse(responseCode = "400", ref = "#/components/responses/400")
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/Mentoring404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<Map<String, String>> RequestMentoring(@Valid @RequestBody RequestMentoringDto dto, Principal principal) {
        User user = userService.getUser(principal.getName());

        return ResponseEntity.ok(participationService.RequestMentoring(dto, user));
    }

    @PostMapping("/club")
    @Operation(summary = "동아리 참가 요청", description = "동아리 참가 요청을 보냅니다.")
    @ApiResponse(responseCode = "200", ref = "#/components/responses/RequestClub200")
    @ApiResponse(responseCode = "400", ref = "#/components/responses/400")
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/Club404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<Map<String, String>> RequestClub(@Valid @RequestBody RequestClubDto dto, Principal principal){
        User user = userService.getUser(principal.getName());

        return ResponseEntity.ok(participationService.requestClub(dto, user));
    }

    @PatchMapping("/mentoring/{id}")
    @Operation(summary = "멘토멘티 신청 승인 및 거절", description = "멘토 신청을 승인 및 거절합니다.")
    @ApiResponse(responseCode = "400", ref = "#/components/responses/400")
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "403", ref = "#/components/responses/403")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/RequestMentoring404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<UpdateMentoringStatusDto> updateMentoringStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMentoringStatusDto dto,
            Principal principal) {
        User user = userService.getUser(principal.getName());

        return ResponseEntity.ok(participationService.updateMentoringStatus(id, dto.getStatus(), user));
    }

    @PatchMapping("/club/{id}")
    @Operation(summary = "동아리 신청 승인 및 거절", description = "동아리 신청을 승인 및 거절합니다.")
    @ApiResponse(responseCode = "400", ref = "#/components/responses/400")
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "403", ref = "#/components/responses/403")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/RequestClub404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<UpdateClubStatusDto> updateClubStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateClubStatusDto dto,
            Principal principal) {
        User user = userService.getUser(principal.getName());

        return ResponseEntity.ok(participationService.updateClubStatus(id, dto.getStatus(), user));
    }

    @GetMapping()
    @Operation(summary = "내 요청 조회", description = "동아리, 프로젝트, 멘토멘티 요청을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "요청 목록 조회 성공",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = MyParticipationDto.class))))
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "403", ref = "#/components/responses/403")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<List<MyParticipationDto>> geMytParticipations(Principal principal){

        User user = userService.getUser(principal.getName());

        return ResponseEntity.ok(participationService.getMyParticipations(user));
    }
}
