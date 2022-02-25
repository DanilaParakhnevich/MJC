package com.epam.esm.validator.exception;

/**
 * The type Unknown certificate exception.
 */
public class UnknownCertificateException extends ValidatorException{
    /**
     * Instantiates a new Unknown certificate exception.
     */
    public UnknownCertificateException() {
        super();
    }

    /**
     * Instantiates a new Unknown certificate exception.
     *
     * @param message the message
     */
    public UnknownCertificateException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Unknown certificate exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UnknownCertificateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Unknown certificate exception.
     *
     * @param cause the cause
     */
    public UnknownCertificateException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Unknown certificate exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected UnknownCertificateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
