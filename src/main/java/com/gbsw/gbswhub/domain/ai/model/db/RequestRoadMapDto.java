package com.gbsw.gbswhub.domain.ai.model.db;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestRoadMapDto {

    @NotBlank(message = "공부 스타일을 입력해주세요")
    @Schema(example = "계획적으로 공부하기")
    private String stduyStyle;

    @NotBlank(message = "공부 분야를 입력해주세요")
    @Schema(example = "백엔드")
    private String stduyField;
}
