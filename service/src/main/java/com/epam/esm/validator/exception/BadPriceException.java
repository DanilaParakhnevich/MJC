package com.epam.esm.validator.exception;

/**
 * The type Bad price exception.
 */
public class BadPriceException extends ValidatorException{
    /**
     * Instantiates a new Bad price exception.
     */
    public BadPriceException() {
        super();
    }

    /**
     * Instantiates a new Bad price exception.
     *
     * @param message the message
     */
    public BadPriceException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Bad price exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public BadPriceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Bad price exception.
     *
     * @param cause the cause
     */
    public BadPriceException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Bad price exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected BadPriceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
