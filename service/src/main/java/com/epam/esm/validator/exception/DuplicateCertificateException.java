package com.epam.esm.validator.exception;

/**
 * The type Duplicate certificate exception.
 */
public class DuplicateCertificateException extends ValidatorException {
    /**
     * Instantiates a new Duplicate certificate exception.
     */
    public DuplicateCertificateException() {
        super();
    }

    /**
     * Instantiates a new Duplicate certificate exception.
     *
     * @param message the message
     */
    public DuplicateCertificateException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Duplicate certificate exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public DuplicateCertificateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Duplicate certificate exception.
     *
     * @param cause the cause
     */
    public DuplicateCertificateException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Duplicate certificate exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected DuplicateCertificateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
