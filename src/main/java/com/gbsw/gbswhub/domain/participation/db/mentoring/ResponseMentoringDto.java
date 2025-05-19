package com.gbsw.gbswhub.domain.participation.db.mentoring;

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
public class ResponseMentoringDto {
    @NotNull(message = "id는 비어있을 수 없습니다.")
    @Schema(example = "1")
    private Long id;

    @NotBlank(message = "역할은 비어있을 수 없습니다.")
    @Schema(example = "백엔드")
    private String position;

    @NotBlank(message = "소개글은 비어있을 수 없습니다.")
    @Schema(example = "열심히 할게요!")
    private String introduce;

    @NotBlank(message = "이름은 비어있을 수 없습니다.")
    @Schema(example = "이수환")
    private String name;

    @NotBlank(message = "학년은 비어있을 수 없습니다")
    @Schema(example = "3학년")
    private String grade;

    @NotBlank(message = "반은 비어있을 수 없습니다.")
    @Schema(example = "1반")
    private String classNo;

    @NotBlank(message = "번호는 비어있을 수 없습니다.")
    @Schema(example = "16번")
    private String studentNo;

    private Participation.Type type = Participation.Type.MENTORING;

    @Schema(example = "REQUESTED")
    @NotNull(message = "상태는 비어있을 수 없습니다.")
    private Participation.Status status = Participation.Status.REQUESTED;
}
