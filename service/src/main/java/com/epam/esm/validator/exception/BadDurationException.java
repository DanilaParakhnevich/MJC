package com.epam.esm.validator.exception;

public class BadDurationException extends ValidatorException{
    public BadDurationException() {
        super();
    }

    public BadDurationException(String message) {
        super(message);
    }

    public BadDurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadDurationException(Throwable cause) {
        super(cause);
    }

    protected BadDurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
