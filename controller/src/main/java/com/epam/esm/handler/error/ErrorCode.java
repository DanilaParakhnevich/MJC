package com.epam.esm.handler.error;


public enum ErrorCode {
    BAD_PARAM(31),
    INVALID_DATE_FORMAT(32),



    INVALID_TAG(11),
    DUPLICATE_TAG(12),
    NONEXISTENT_TAG(13),

    INVALID_CERTIFICATE(21),
    DUPLICATE_CERTIFICATE(22),
    NONEXISTENT_CERTIFICATE(23);

    private final long code;

    ErrorCode(long code) {
        this.code = code;
    }

    public long getCode() {
        return code;
    }
}
