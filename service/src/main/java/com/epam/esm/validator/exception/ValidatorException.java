package com.epam.esm.validator.exception;

/**
 * The type Validator exception.
 */
public class ValidatorException extends RuntimeException{
    /**
     * Instantiates a new Validator exception.
     */
    public ValidatorException() {
        super();
    }

    /**
     * Instantiates a new Validator exception.
     *
     * @param message the message
     */
    public ValidatorException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Validator exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Validator exception.
     *
     * @param cause the cause
     */
    public ValidatorException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Validator exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected ValidatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
