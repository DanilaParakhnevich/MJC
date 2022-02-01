package com.epam.esm.validator.exception;

public class InvalidDateFormatException extends ValidatorException {
    public InvalidDateFormatException() {
        super();
    }

    public InvalidDateFormatException(String message) {
        super(message);
    }

    public InvalidDateFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDateFormatException(Throwable cause) {
        super(cause);
    }

    protected InvalidDateFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
