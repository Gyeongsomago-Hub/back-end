package com.gbsw.gbswhub.domain.project.db;


import com.gbsw.gbswhub.domain.project.model.Project;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    @Schema(example = "1")
    private Long id;

    @Schema(example = "저랑 같이 프로젝트 하실분")
    private String title;

    @Schema(example = "사이드 프로젝트임")
    private String content;

    @Schema(example = "1~2명")
    private String people;

    @Schema(example = "100")
    private Long view_count;

    @Schema(example = "[\"ios\", \"frontend\", \"backend\"]")
    private List<String> stack;

    private Project.Status status = Project.Status.RECRUITING;
}
