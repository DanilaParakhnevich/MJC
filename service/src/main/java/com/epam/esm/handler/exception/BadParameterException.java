package com.epam.esm.handler.exception;

/**
 * The type Bad parameter exception.
 */
public class BadParameterException extends RuntimeException{
    /**
     * Instantiates a new Bad parameter exception.
     */
    public BadParameterException() {
        super();
    }

    /**
     * Instantiates a new Bad parameter exception.
     *
     * @param message the message
     */
    public BadParameterException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Bad parameter exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public BadParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Bad parameter exception.
     *
     * @param cause the cause
     */
    public BadParameterException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Bad parameter exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected BadParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
