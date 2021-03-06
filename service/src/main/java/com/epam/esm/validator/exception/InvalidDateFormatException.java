package com.epam.esm.validator.exception;

/**
 * The type Invalid date format exception.
 */
public class InvalidDateFormatException extends ValidatorException {
    /**
     * Instantiates a new Invalid date format exception.
     */
    public InvalidDateFormatException() {
        super();
    }

    /**
     * Instantiates a new Invalid date format exception.
     *
     * @param message the message
     */
    public InvalidDateFormatException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Invalid date format exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public InvalidDateFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Invalid date format exception.
     *
     * @param cause the cause
     */
    public InvalidDateFormatException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Invalid date format exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected InvalidDateFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
