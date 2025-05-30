package com.gbsw.gbswhub.domain.project.mentoring.db;

import com.gbsw.gbswhub.domain.project.model.Project;
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
public class MentoringDto {

    @Schema(example = "1")
    private Long id;

    @NotBlank(message = "제목을 입력해주세요.")
    @Schema(example = "백엔드 알려주세요")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Schema(example = "백엔드 잘하시는분 도와줘요")
    private String content;

    @NotBlank(message = "인원수를 입력해주세요.")
    @Schema(example = "1~2명")
    private String people;

    @Schema(example = "100")
    private Long view_count;

    @NotEmpty(message = "스택을 입력해주세요.")
    @Schema(example = "[\"ios\", \"frontend\", \"backend\"]")
    private List<@NotBlank String> stack;


    @NotNull(message = "시작일을 입력해주세요.")
    @Schema(example = "2025-04-18")
    private LocalDate openDate;

    @NotNull(message = "마감일을 입력해주세요.")
    @Schema(example = "2023-04-25")
    private LocalDate closeDate;

    private Project.Status status = Project.Status.RECRUITING;

    @NotNull(message = "카테고리를 선택해주세요.")
    @Schema(description = "카테고리 테이블에 저장된 ID", example = "1")
    private Long categoryId;

    @NotNull(message = "사용자 ID를 입력해주세요.")
    @Schema(example = "1")
    private Long userId;

    @Schema(example = "이수환")
    private String name;
}
