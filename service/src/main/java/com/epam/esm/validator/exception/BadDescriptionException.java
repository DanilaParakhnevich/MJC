package com.epam.esm.validator.exception;

public class BadDescriptionException extends ValidatorException{
    public BadDescriptionException() {
        super();
    }

    public BadDescriptionException(String message) {
        super(message);
    }

    public BadDescriptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadDescriptionException(Throwable cause) {
        super(cause);
    }

    protected BadDescriptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
