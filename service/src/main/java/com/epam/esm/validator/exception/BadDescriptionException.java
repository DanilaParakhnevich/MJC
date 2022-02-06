package com.epam.esm.validator.exception;

/**
 * The type Bad description exception.
 */
public class BadDescriptionException extends ValidatorException{
    /**
     * Instantiates a new Bad description exception.
     */
    public BadDescriptionException() {
        super();
    }

    /**
     * Instantiates a new Bad description exception.
     *
     * @param message the message
     */
    public BadDescriptionException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Bad description exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public BadDescriptionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Bad description exception.
     *
     * @param cause the cause
     */
    public BadDescriptionException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Bad description exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected BadDescriptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
