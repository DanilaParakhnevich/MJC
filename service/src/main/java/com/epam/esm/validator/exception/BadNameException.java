package com.epam.esm.validator.exception;

/**
 * The type Bad name exception.
 */
public class BadNameException extends ValidatorException{
    /**
     * Instantiates a new Bad name exception.
     */
    public BadNameException() {
        super();
    }

    /**
     * Instantiates a new Bad name exception.
     *
     * @param message the message
     */
    public BadNameException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Bad name exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public BadNameException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Bad name exception.
     *
     * @param cause the cause
     */
    public BadNameException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Bad name exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected BadNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
