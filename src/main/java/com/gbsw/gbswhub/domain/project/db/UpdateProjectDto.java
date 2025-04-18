package com.gbsw.gbswhub.domain.project.db;

import com.gbsw.gbswhub.domain.project.model.Project;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProjectDto {
    @NotBlank(message = "제목은 비어있을 수 없습니다.")
    @Schema(example = "저랑 같이 프로젝트 하실분")
    private String title;

    @NotBlank(message = "내용은 비어있을 수 없습니다.")
    @Schema(example = "사이드 프로젝트임")
    private String content;

    @NotBlank(message = "인원수는 비어있을 수 없습니다.")
    @Schema(example = "1~2명")
    private String people;

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
}
