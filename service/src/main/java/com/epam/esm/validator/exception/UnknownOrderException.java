package com.epam.esm.validator.exception;

public class UnknownOrderException extends ValidatorException {
    public UnknownOrderException() {
        super();
    }

    public UnknownOrderException(String message) {
        super(message);
    }

    public UnknownOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownOrderException(Throwable cause) {
        super(cause);
    }

    protected UnknownOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
