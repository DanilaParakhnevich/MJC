package com.epam.esm.handler.error;

import lombok.Data;

@Data()
public class ErrorResponse {
    private long code;
    private String message;

    public ErrorResponse(long code, String message) {
        this.code = code;
        this.message = message;
    }
}
