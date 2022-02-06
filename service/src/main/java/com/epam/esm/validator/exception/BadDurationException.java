package com.epam.esm.validator.exception;

/**
 * The type Bad duration exception.
 */
public class BadDurationException extends ValidatorException{
    /**
     * Instantiates a new Bad duration exception.
     */
    public BadDurationException() {
        super();
    }

    /**
     * Instantiates a new Bad duration exception.
     *
     * @param message the message
     */
    public BadDurationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Bad duration exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public BadDurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Bad duration exception.
     *
     * @param cause the cause
     */
    public BadDurationException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Bad duration exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected BadDurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
