package com.gbsw.gbswhub.domain.jwt.db;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccessTokenByRefreshToken {
    @Schema(example = "로그인 할때 발급 받은 refreshToken 넣기")
    private String refreshToken;
}
