package com.gbsw.gbswhub.domain.project.db;


import com.gbsw.gbswhub.domain.project.model.Project;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    @Schema(example = "1")
    private Long id;

    @NotBlank(message = "제목은 비어있을 수 없습니다.")
    @Schema(example = "저랑 같이 프로젝트 하실분")
    private String title;

    @NotBlank(message = "내용은 비어있을 수 없습니다.")
    @Schema(example = "사이드 프로젝트임")
    private String content;

    @NotBlank(message = "인원수는 비어있을 수 없습니다.")
    @Schema(example = "1~2명")
    private String people;

    @Schema(example = "100")
    private Long view_count;

    @NotEmpty(message = "스택은 비어있을 수 없습니다.")
    @Schema(example = "[\"ios\", \"frontend\", \"backend\"]")
    private List<@NotBlank String> stack;

    @NotNull(message = "시작일은 비어있을 수 없습니다.")
    @Schema(example = "2025-04-18")
    private LocalDate openDate;

    @NotNull(message = "마감일은 비어있을 수 없습니다.")
    @Schema(example = "2023-04-25")
    private LocalDate closeDate;

    private Project.Status status = Project.Status.RECRUITING;

    @NotNull(message = "사용자 ID를 입력해주세요.")
    @Schema(example = "1")
    private Long userId;

    @Schema(example = "이수환")
    private String name;
}
