package com.epam.esm.handler.error;


/**
 * The enum Error code.
 */
public enum ErrorCode {
    /**
     * Bad param error code.
     */
    BAD_PARAM(31),
    /**
     * Invalid date format error code.
     */
    INVALID_DATE_FORMAT(32),
    /**
     * Bad value error code.
     */
    BAD_VALUE(33),

    /**
     * Duplicate tag error code.
     */
    DUPLICATE_TAG(12),
    /**
     * Nonexistent tag error code.
     */
    NONEXISTENT_TAG(13),

    /**
     * Duplicate certificate error code.
     */
    DUPLICATE_CERTIFICATE(22),
    /**
     * Nonexistent certificate error code.
     */
    NONEXISTENT_CERTIFICATE(23);

    private final long code;

    ErrorCode(long code) {
        this.code = code;
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public long getCode() {
        return code;
    }
}
