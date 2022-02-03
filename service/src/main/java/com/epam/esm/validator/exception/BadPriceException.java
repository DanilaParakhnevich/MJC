package com.epam.esm.validator.exception;

public class BadPriceException extends ValidatorException{
    public BadPriceException() {
        super();
    }

    public BadPriceException(String message) {
        super(message);
    }

    public BadPriceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadPriceException(Throwable cause) {
        super(cause);
    }

    protected BadPriceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
