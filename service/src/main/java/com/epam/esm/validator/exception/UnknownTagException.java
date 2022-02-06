package com.epam.esm.validator.exception;

/**
 * The type Unknown tag exception.
 */
public class UnknownTagException extends ValidatorException{
    /**
     * Instantiates a new Unknown tag exception.
     */
    public UnknownTagException() {
        super();
    }

    /**
     * Instantiates a new Unknown tag exception.
     *
     * @param message the message
     */
    public UnknownTagException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Unknown tag exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UnknownTagException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Unknown tag exception.
     *
     * @param cause the cause
     */
    public UnknownTagException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Unknown tag exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected UnknownTagException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
