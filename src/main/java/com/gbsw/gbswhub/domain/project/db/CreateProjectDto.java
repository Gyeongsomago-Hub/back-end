package com.gbsw.gbswhub.domain.project.db;


import com.gbsw.gbswhub.domain.project.model.Project.Status;
import com.gbsw.gbswhub.domain.project.model.Project.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
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
public class CreateProjectDto {

    @NotBlank(message = "제목을 입력해주세요.")
    @Schema(example = "저랑 같이 프로젝트 하실분")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Schema(example = "사이드 프로젝트임")
    private String content;

    @NotBlank(message = "인원수를 입력해주세요.")
    @Schema(example = "1~2명")
    private String people;

    @NotEmpty(message = "스택을 입력해주세요.")
    @Schema(example = "[\"ios\", \"frontend\", \"backend\"]")
    private List<@NotBlank String> stacks;

    @NotNull(message = "시작일을 입력해주세요.")
    @Schema(example = "2025-04-18")
    private LocalDate openDate;

    @NotNull(message = "마감일을 입력해주세요.")
    @Schema(example = "2023-04-25")
    private LocalDate closeDate;


    private Status status = Status.RECRUITING;

    private Type type = Type.PROJECT;
}
