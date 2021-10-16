package com.example.superbank.exception;

public class ExistingResourceException extends RuntimeException{

    public ExistingResourceException() {
    }

    public ExistingResourceException(String message) {
        super(message);
    }

    public ExistingResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
