package com.epam.esm.validator.exception;

public class DuplicateTagException extends ValidatorException{
    public DuplicateTagException() {
        super();
    }

    public DuplicateTagException(String message) {
        super(message);
    }

    public DuplicateTagException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateTagException(Throwable cause) {
        super(cause);
    }

    protected DuplicateTagException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
