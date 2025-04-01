package com.gbsw.gbswhub.domain.jwt.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenResponse {
    private String message;
    private String token;
    private String refreshToken;

    public AccessTokenResponse(String message) {
        this.message = message;
        this.token = null;
        this.refreshToken = null;
    }
}
