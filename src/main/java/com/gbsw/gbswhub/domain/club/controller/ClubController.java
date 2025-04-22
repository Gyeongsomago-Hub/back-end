package com.gbsw.gbswhub.domain.club.controller;


import com.gbsw.gbswhub.domain.club.db.CreateClubDto;
import com.gbsw.gbswhub.domain.club.service.ClubService;
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
    @ApiResponse(responseCode = "404", ref = "#/components/responses/404")
    @ApiResponse(responseCode = "500", ref = "#/components/responses/500")
    public ResponseEntity<Map<String, String>> createClub(@Valid @RequestBody CreateClubDto dto, Principal principal) {
        User user = userService.getUser(principal.getName());

        return ResponseEntity.ok(clubService.createClub(dto, user));
    }
}
