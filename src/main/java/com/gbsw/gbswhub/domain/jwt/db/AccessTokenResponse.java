package com.gbsw.gbswhub.domain.jwt.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccessTokenResponse {
    private String message;
    private String token;
    private String refreshToken;

    public AccessTokenResponse(String string) {
        this.message = string;
        this.token = null;
        this.refreshToken = null;
    }
}
