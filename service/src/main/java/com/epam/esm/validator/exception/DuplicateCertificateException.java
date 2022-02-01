package com.epam.esm.validator.exception;

public class DuplicateCertificateException extends ValidatorException {
    public DuplicateCertificateException() {
        super();
    }

    public DuplicateCertificateException(String message) {
        super(message);
    }

    public DuplicateCertificateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateCertificateException(Throwable cause) {
        super(cause);
    }

    protected DuplicateCertificateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
