package com.gbsw.gbswhub.domain.participation.db.project;

import com.gbsw.gbswhub.domain.participation.model.Participation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ResponseProjectDto {
    @NotNull(message = "id는 비어있을 수 없습니다.")
    @Schema(example = "1")
    private Long id;

    @NotBlank(message = "역할은 비어있을 수 없습니다.")
    @Schema(example = "백엔드")
    private String position;

    @NotNull(message = "프로젝트를 선택해주세요.")
    @Schema(example = "1")
    private Long ProjectId;

    private Participation.Type type = Participation.Type.PROJECT;

    @Schema(example = "APPROVED")
    private Participation.Status status = Participation.Status.APPROVED;
}
