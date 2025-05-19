package com.gbsw.gbswhub.domain.jwt.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccessTokenResponse {
    private String message;
    private String token;
    private String refreshToken;
}
