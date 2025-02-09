package com.api.gateway.util;

import com.api.gateway.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Utility {

    public static <T> ResponseEntity<ApiResponse<T>> errorObject(String message, HttpStatus errorCode) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setApiStatus("FAILURE");
        response.setApiVersion("1.0");
        response.setErrorCode(errorCode.name());
        response.setMessage(message);
        return new ResponseEntity<>(response, errorCode);
    }
}
