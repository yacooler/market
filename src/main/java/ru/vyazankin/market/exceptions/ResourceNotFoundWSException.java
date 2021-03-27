package ru.vyazankin.market.exceptions;

public class ResourceNotFoundWSException extends RuntimeException{
    public ResourceNotFoundWSException() {
    }

    public ResourceNotFoundWSException(String message) {
        super(message);
    }

    public ResourceNotFoundWSException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundWSException(Throwable cause) {
        super(cause);
    }

    public ResourceNotFoundWSException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
