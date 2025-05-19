package com.gbsw.gbswhub.domain.project.mentoring.controller;

import com.gbsw.gbswhub.domain.project.Service.MentoringService;
import com.gbsw.gbswhub.domain.project.mentoring.db.CreateMentoringDto;
import com.gbsw.gbswhub.domain.project.mentoring.db.MentoringDto;
import com.gbsw.gbswhub.domain.project.mentoring.db.UpdateMentoringDto;
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
    @ApiResponse(responseCode = "404", ref = "#/components/responses/Category404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<Map<String, String>> createMentoring(
            @Valid @RequestBody CreateMentoringDto createMentoringDto,
            Principal principal
    ) {
        User user = userService.getUser(principal.getName());
        return ResponseEntity.ok(mentoringService.createMentoring(createMentoringDto, user));
    }

    @GetMapping()
    @Operation(summary = "멘토멘티 전체 조회", description = "멘토멘티 모집을 전체 조회합니다.")
    @ApiResponse(responseCode = "200", description = "멘토멘티 모집 목록 조회 성공",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = MentoringDto.class))))
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/Category404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<List<MentoringDto>> getAllMentoring(){
        return ResponseEntity.ok(mentoringService.getAllMentoring());
    }

    @GetMapping("/{id}")
    @Operation(summary = "멘토멘티 하나 조회", description = "멘토멘티 모집을 하나 조회합니다.")
    @ApiResponse(responseCode = "200", description = "멘토멘티 모집 하나 조회 성공",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MentoringDto.class)))
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/Mentoring404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<MentoringDto> getMentoringById(@PathVariable Long id){
        MentoringDto mentoring = mentoringService.getMentoringById(id);

        return ResponseEntity.ok(mentoring);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "멘토멘티 수정", description = "멘토멘티 모집을 수정합니다.")
    @ApiResponse(responseCode = "400", ref = "#/components/responses/400")
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "403", ref = "#/components/responses/403")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/Mentoring404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<MentoringDto> updateMentoring(
            @PathVariable Long id,
            @Valid @RequestBody  UpdateMentoringDto dto,
            Principal principal) {

        User user = userService.getUser(principal.getName());

        MentoringDto mentoring = mentoringService.updateMentoring(id, dto, user);

        return ResponseEntity.ok(mentoring);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "멘토멘티 삭제", description = "멘토멘티 모집을 삭제합니다.")
    @ApiResponse(responseCode = "204", ref = "#/components/responses/204")
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "403", ref = "#/components/responses/403")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/Mentoring404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<Void> deleteMentoring(@PathVariable Long id, Principal principal){

        User user = userService.getUser(principal.getName());

        mentoringService.deleteMentoring(id, user);

        return ResponseEntity.noContent().build();
    }
}
