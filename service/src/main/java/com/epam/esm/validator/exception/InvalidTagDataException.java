package com.epam.esm.validator.exception;

public class InvalidTagDataException extends ValidatorException{
    public InvalidTagDataException() {
        super();
    }

    public InvalidTagDataException(String message) {
        super(message);
    }

    public InvalidTagDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTagDataException(Throwable cause) {
        super(cause);
    }

    protected InvalidTagDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
