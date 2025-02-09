package com.api.gateway.exception;

public class ApiGatewayException extends RuntimeException{

    public ApiGatewayException(String message) {
        super(message);
    }
}
