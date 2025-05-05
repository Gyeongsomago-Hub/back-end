package com.gbsw.gbswhub.domain.participation.db;

import com.gbsw.gbswhub.domain.participation.model.Participation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyParticipationDto {

    @Schema(example = "1")
    private String part_id;

    @Schema(example = "동아리에 가입하고 싶어요")
    private String introduce;

    @Schema(example = "백엔드")
    private String position;

    @Schema(example = "PROJECT")
    private Participation.Type type;

    @Schema(example = "REQUESTED")
    private Participation.Status status;

}
