package com.api.gateway.exception;

public class CustomExpiredJwtException extends RuntimeException {

    public CustomExpiredJwtException(String message) {
        super(message);
    }

    public CustomExpiredJwtException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
