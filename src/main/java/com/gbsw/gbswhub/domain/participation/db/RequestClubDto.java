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
public class RequestClubDto {

    @NotBlank(message = "소개글을  입력해주세요.")
    @Schema(example = "동아리에 가입하고싶어요.")
    private String introduce;

    @NotBlank(message = "이름을  입력해주세요.")
    @Schema(example = "이수환")
    private String name;

    @NotBlank(message = "학년을  입력해주세요.")
    @Schema(example = "3학년")
    private String grade;

    @NotBlank(message = "반을  입력해주세요.")
    @Schema(example = "1반")
    private String classNo;

    @NotBlank(message = "번호를 입력해주세요.")
    @Schema(example = "16번")
    private String studentNo;

    @NotNull(message = "동아리 ID를 입력해주세요.")
    @Schema(example = "1")
    private Long clubId;

    @Schema(example = "CLUB")
    private Participation.Type type = Participation.Type.CLUB;

    @Schema(example = "REQUESTED")
    private Participation.Status status = Participation.Status.REQUESTED;
}
