package com.gbsw.gbswhub.domain.user.db;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원가입을 성공했을 시에 response로 돌아오는 응답")
public class CreateUserResult {
    @Schema(example = "회원가입 성공")
    private String message;

    @Schema(example = "suhwan3116")
    private String username;
}
