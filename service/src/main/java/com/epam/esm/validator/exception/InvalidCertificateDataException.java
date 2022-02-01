package com.epam.esm.validator.exception;

public class InvalidCertificateDataException extends ValidatorException{
    public InvalidCertificateDataException() {
        super();
    }

    public InvalidCertificateDataException(String message) {
        super(message);
    }

    public InvalidCertificateDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCertificateDataException(Throwable cause) {
        super(cause);
    }

    protected InvalidCertificateDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
