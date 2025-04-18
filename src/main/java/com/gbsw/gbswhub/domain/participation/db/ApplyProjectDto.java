package com.gbsw.gbswhub.domain.participation.db;
import com.gbsw.gbswhub.domain.participation.model.Participation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApplyProjectDto {
    @NotBlank(message = "역할을 선택해주세요.")
    @Schema(example = "백엔드")
    private String position;

    private Participation.Type type = Participation.Type.PROJECT;

    private Participation.Status status = Participation.Status.REQUESTED;
}
