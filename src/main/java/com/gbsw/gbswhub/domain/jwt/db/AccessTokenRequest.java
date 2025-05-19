package com.gbsw.gbswhub.domain.jwt.db;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccessTokenRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Schema(example = "suhwan3116")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Schema(example = "Hwan1234@")
    private String password;
}
