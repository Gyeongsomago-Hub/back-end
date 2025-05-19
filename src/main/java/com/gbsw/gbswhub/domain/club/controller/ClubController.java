package com.gbsw.gbswhub.domain.club.controller;


import com.gbsw.gbswhub.domain.club.db.ClubDto;
import com.gbsw.gbswhub.domain.club.db.CreateClubDto;
import com.gbsw.gbswhub.domain.club.db.UpdateClubDto;
import com.gbsw.gbswhub.domain.club.service.ClubService;
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
@RequestMapping("/api/club")
@Tag(name = "Club", description = "Club에 관한 API")
public class ClubController {

    private final ClubService clubService;
    private final UserService userService;


    @PostMapping()
    @Operation(summary = "동아리 생성", description = "동아리 모집 공고를 생성합니다.")
    @ApiResponse(responseCode = "200", ref = "#/components/responses/Club200")
    @ApiResponse(responseCode = "400", ref = "#/components/responses/400")
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "403", ref = "#/components/responses/403")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<Map<String, String>> createClub(@Valid @RequestBody CreateClubDto dto, Principal principal) {
        User user = userService.getUser(principal.getName());

        return ResponseEntity.ok(clubService.createClub(dto, user));
    }

    @GetMapping()
    @Operation(summary = "동아리 전체 조회", description = "동아리 모집 공고 전체 조회합니다.")
    @ApiResponse(responseCode = "200", description = "동아리 모집 공고 목록 조회 성공",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ClubDto.class))))
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<List<ClubDto>> getAllClub(){
        return ResponseEntity.ok(clubService.getAllClub());
    }

    @GetMapping("/{id}")
    @Operation(summary = "동아리 하나 조회", description = "동아리 모집 공고를 하나 조회합니다.")
    @ApiResponse(responseCode = "200", description = "프로젝트 모집 조회 성공",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ClubDto.class)))
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/Club404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<ClubDto> getClubById(@PathVariable Long id){
        ClubDto clubDto = clubService.getClubById(id);

        return ResponseEntity.ok(clubDto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "동아리 수정", description = "동아리 모집 공고를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "프로젝트 모집 수정 성공",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ClubDto.class)))
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "403", ref = "#/components/responses/403")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/Club404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<ClubDto> updateClub(@PathVariable Long id, @Valid @RequestBody UpdateClubDto dto, Principal principal) {
        User user = userService.getUser(principal.getName());

        return ResponseEntity.ok(clubService.updateClub(id, dto, user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "동아리 삭제", description = "동아리 모집 공고를 삭제합니다.")
    @ApiResponse(responseCode = "204", ref = "#/components/responses/204")
    @ApiResponse(responseCode = "401", ref = "#/components/responses/Login401")
    @ApiResponse(responseCode = "403", ref = "#/components/responses/403")
    @ApiResponse(responseCode = "404", ref = "#/components/responses/Club404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public void deleteClub(@PathVariable Long id, Principal principal){
        User user = userService.getUser(principal.getName());

        clubService.deleteClub(id, user);
        ResponseEntity.noContent().build();
    }
}
