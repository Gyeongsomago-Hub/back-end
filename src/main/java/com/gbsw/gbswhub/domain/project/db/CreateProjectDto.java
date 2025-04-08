package com.gbsw.gbswhub.domain.project.db;


import com.gbsw.gbswhub.domain.project.model.Project.Status;
import com.gbsw.gbswhub.domain.project.model.Project.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private Status status = Status.RECRUITING;

    private Type type = Type.PROJECT;
}
