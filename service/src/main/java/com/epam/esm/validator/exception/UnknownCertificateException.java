package com.epam.esm.validator.exception;

public class UnknownCertificateException extends ValidatorException{
    public UnknownCertificateException() {
        super();
    }

    public UnknownCertificateException(String message) {
        super(message);
    }

    public UnknownCertificateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownCertificateException(Throwable cause) {
        super(cause);
    }

    protected UnknownCertificateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
