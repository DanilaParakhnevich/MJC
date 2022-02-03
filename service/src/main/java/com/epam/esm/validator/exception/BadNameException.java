package com.epam.esm.validator.exception;

public class BadNameException extends ValidatorException{
    public BadNameException() {
        super();
    }

    public BadNameException(String message) {
        super(message);
    }

    public BadNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadNameException(Throwable cause) {
        super(cause);
    }

    protected BadNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
