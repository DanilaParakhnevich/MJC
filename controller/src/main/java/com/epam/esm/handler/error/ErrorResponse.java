package com.epam.esm.handler.error;

import lombok.Data;

/**
 * The type Error response.
 */
@Data()
public class ErrorResponse {
    private long code;
    private String message;

    /**
     * Instantiates a new Error response.
     *
     * @param code    the code
     * @param message the message
     */
    public ErrorResponse(long code, String message) {
        this.code = code;
        this.message = message;
    }
}
