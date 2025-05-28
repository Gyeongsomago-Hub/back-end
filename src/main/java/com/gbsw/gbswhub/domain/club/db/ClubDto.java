package com.gbsw.gbswhub.domain.club.db;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubDto {

    @Schema(example = "1")
    private Long id;

    @NotBlank(message = "동아리명은 비어있을 수 없습니다.")
    @Schema(example = "웹개발 동아리")
    private String name;

    @NotBlank(message = "설명은 비어있을 수 없습니다.")
    @Schema(example = "웹개발에 대해 공부하는 동아리")
    private String description;

    @NotBlank(message = "장소는 비어있을 수 없습니다.")
    @Schema(example = "프로젝트 실습실")
    private String location;

    @NotBlank(message = "대상학년은 비어있을 수 없습니다.")
    @Schema(example = "3학년")
    private String target;

    @NotBlank(message = "동아리 유형은 비어있을 수 없습니다.")
    @Schema(example = "전공")
    private String type;

    @NotNull(message = "시작일은 비어있을 수 없습니다.")
    @Schema(example = "2025-04-18")
    private LocalDate openDate;

    @NotNull(message = "마감일은 비어있을 수 없습니다.")
    @Schema(example = "2023-04-25")
    private LocalDate closeDate;

    @NotNull(message = "사용자 ID를 입력해주세요.")
    @Schema(example = "1")
    private Long userId;

    @Schema(example = "이수환")
    private String userName;
}
