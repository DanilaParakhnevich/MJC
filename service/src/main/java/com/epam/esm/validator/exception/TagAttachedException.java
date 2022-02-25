package com.epam.esm.validator.exception;

public class TagAttachedException extends ValidatorException{
    public TagAttachedException() {
        super();
    }

    public TagAttachedException(String message) {
        super(message);
    }

    public TagAttachedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TagAttachedException(Throwable cause) {
        super(cause);
    }

    protected TagAttachedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
