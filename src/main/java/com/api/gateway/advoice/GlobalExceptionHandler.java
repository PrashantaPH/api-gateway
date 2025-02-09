package com.api.gateway.advoice;

import com.api.gateway.dto.ApiResponse;
import com.api.gateway.exception.ApiGatewayException;
import com.api.gateway.exception.CustomExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.api.gateway.util.Utility.errorObject;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomExpiredJwtException.class)
    public ResponseEntity<ApiResponse<Object>> handleExpiredJwtException(CustomExpiredJwtException exception){
        return errorObject(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ApiGatewayException.class)
    public ResponseEntity<ApiResponse<Object>> HandleTechnicalException(ApiGatewayException exception) {
        return errorObject(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
