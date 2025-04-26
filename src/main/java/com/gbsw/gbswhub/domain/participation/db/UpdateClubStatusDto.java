package com.gbsw.gbswhub.domain.participation.db;

import com.gbsw.gbswhub.domain.participation.model.Participation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateClubStatusDto {

    @NotNull(message = "상태를 입력해주세요.")
    @Schema(example = "APPROVED")
    private Participation.Status status;
}
