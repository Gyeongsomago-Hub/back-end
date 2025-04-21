package com.gbsw.gbswhub.domain.participation.db;
import com.gbsw.gbswhub.domain.participation.model.Participation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestProjectDto {
    @NotBlank(message = "역할을 선택해주세요.")
    @Schema(example = "백엔드")
    private String position;

    @NotNull(message = "프로젝트를 선택해주세요.")
    @Schema(example = "1")
    private Long ProjectId;

    private Participation.Type type = Participation.Type.PROJECT;

    @Schema(example = "REQUESTED")
    private Participation.Status status = Participation.Status.REQUESTED;
}
