package com.gbsw.gbswhub.domain.global.Error;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final String message;
    private final int status;

    public ErrorResponse(ErrorCode errorcode) {
        this.message = errorcode.getMessage();
        this.status = errorcode.getStatus().value();
    }
}
