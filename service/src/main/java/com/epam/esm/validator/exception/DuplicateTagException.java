package com.epam.esm.validator.exception;

/**
 * The type Duplicate tag exception.
 */
public class DuplicateTagException extends ValidatorException{
    /**
     * Instantiates a new Duplicate tag exception.
     */
    public DuplicateTagException() {
        super();
    }

    /**
     * Instantiates a new Duplicate tag exception.
     *
     * @param message the message
     */
    public DuplicateTagException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Duplicate tag exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public DuplicateTagException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Duplicate tag exception.
     *
     * @param cause the cause
     */
    public DuplicateTagException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Duplicate tag exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected DuplicateTagException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
