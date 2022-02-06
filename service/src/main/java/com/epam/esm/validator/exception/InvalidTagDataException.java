package com.epam.esm.validator.exception;

/**
 * The type Invalid tag data exception.
 */
public class InvalidTagDataException extends ValidatorException{
    /**
     * Instantiates a new Invalid tag data exception.
     */
    public InvalidTagDataException() {
        super();
    }

    /**
     * Instantiates a new Invalid tag data exception.
     *
     * @param message the message
     */
    public InvalidTagDataException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Invalid tag data exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public InvalidTagDataException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Invalid tag data exception.
     *
     * @param cause the cause
     */
    public InvalidTagDataException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Invalid tag data exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected InvalidTagDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
