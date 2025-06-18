package com.gbsw.gbswhub.domain.participation.controller;

import com.gbsw.gbswhub.domain.participation.db.*;
import com.gbsw.gbswhub.domain.participation.db.club.RequestClubDto;
import com.gbsw.gbswhub.domain.participation.db.club.ResponseClubDto;
import com.gbsw.gbswhub.domain.participation.db.club.UpdateClubStatusDto;
import com.gbsw.gbswhub.domain.participation.db.mentoring.RequestMentoringDto;
import com.gbsw.gbswhub.domain.participation.db.mentoring.ResponseMentoringDto;
import com.gbsw.gbswhub.domain.participation.db.mentoring.UpdateMentoringStatusDto;
import com.gbsw.gbswhub.domain.participation.db.project.RequestProjectDto;
import com.gbsw.gbswhub.domain.participation.db.project.ResponseProjectDto;
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

    @DeleteMapping("/{id}")
    @Operation(summary = "참가 요청 삭제", description = ".")
    @ApiResponse(responseCode = "204", ref = "#/components/responses/204")
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "403", ref = "#/components/responses/403")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<Void> deleteParticipation(@PathVariable Long id, Principal principal) {
        User user = userService.getUser(principal.getName());

        participationService.deleteParticipation(id, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/project/{id}")
    @Operation(summary = "특정 프로젝트 신청 내역 조회", description = "프로젝트 글쓴이가 자신의 프로젝트 신청 내역을 모두 조회합니다.")
    @ApiResponse(responseCode = "200", description = "요청 목록 조회 성공",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ResponseProjectDto.class))))
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "403", ref = "#/components/responses/403")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<List<ResponseProjectDto>> getAllProjectById(@PathVariable Long id, Principal principal) {
        User user = userService.getUser(principal.getName());

        List<ResponseProjectDto> dtoList = participationService.getRequestByProject(user, id);

        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/club/{id}")
    @Operation(summary = "특정 동아리 신청 내역 조회", description = "동아리장이 자신의 동아리의 신청 내역을 모두 조회합니다.")
    @ApiResponse(responseCode = "200", description = "요청 목록 조회 성공",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ResponseClubDto.class))))
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "403", ref = "#/components/responses/403")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<List<ResponseClubDto>> getAllClubById(@PathVariable Long id, Principal principal){
        User user = userService.getUser(principal.getName());

        List<ResponseClubDto> dtoList = participationService.getRequestByClub(user, id);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/mentoring/{id}")
    @Operation(summary = "특정 멘토멘티 신청 내역 조회", description = "멘토가 자신의 멘토링 신청 내역을 모두 조회합니다.")
    @ApiResponse(responseCode = "200", description = "요청 목록 조회 성공",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ResponseClubDto.class))))
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "403", ref = "#/components/responses/403")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<List<ResponseMentoringDto>> getAllMentoringById(@PathVariable Long id, Principal principal) {
        User user = userService.getUser(principal.getName());

        List<ResponseMentoringDto> dtoList = participationService.getRequestByMentoring(user, id);

        return ResponseEntity.ok(dtoList);
    }
}
