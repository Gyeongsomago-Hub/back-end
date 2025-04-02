package com.gbsw.gbswhub.domain.jwt.db;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccessTokenByRefreshToken {
    private String refreshToken;
}
