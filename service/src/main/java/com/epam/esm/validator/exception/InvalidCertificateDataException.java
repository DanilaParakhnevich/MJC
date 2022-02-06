package com.epam.esm.validator.exception;

/**
 * The type Invalid certificate data exception.
 */
public class InvalidCertificateDataException extends ValidatorException{
    /**
     * Instantiates a new Invalid certificate data exception.
     */
    public InvalidCertificateDataException() {
        super();
    }

    /**
     * Instantiates a new Invalid certificate data exception.
     *
     * @param message the message
     */
    public InvalidCertificateDataException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Invalid certificate data exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public InvalidCertificateDataException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Invalid certificate data exception.
     *
     * @param cause the cause
     */
    public InvalidCertificateDataException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Invalid certificate data exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected InvalidCertificateDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
