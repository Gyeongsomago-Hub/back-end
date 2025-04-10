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

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMentoringDto {
    @NotBlank(message = "제목을 입력해주세요.")
    @Schema(example = "백엔드 알려주세요")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Schema(example = "백엔드 잘하시는분 도와줘요")
    private String content;

    @NotBlank(message = "인원수를 입력해주세요.")
    @Schema(example = "1~2명")
    private String people;

    @NotEmpty(message = "스택을 입력해주세요.")
    @Schema(example = "[\"ios\", \"frontend\", \"backend\"]")
    private List<@NotBlank String> stacks;

    private Project.Status status = Project.Status.RECRUITING;

    @Schema(example = "MENTORING")
    private Project.Type type = Project.Type.MENTORING;

    @NotNull(message = "카테고리를 선택해주세요.")
    @Schema(description = "카테고리 테이블에 저장된 ID", example = "1")
    private Long categoryId;
}
