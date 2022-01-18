package com.alissonpedrina.java17.api.advice;

import com.alissonpedrina.java17.api.error.DojoErrorException;
import com.alissonpedrina.java17.api.error.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class DojoAdvice {

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse jsonProcessingException(JsonProcessingException e) {
        return createErrorDTO(HttpStatus.NOT_FOUND, "DojoError: JsonProcessing error.", e.getMessage());

    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse processDojoErrorException(HttpClientErrorException e) {
        return createErrorDTO(HttpStatus.NOT_FOUND, "DojoError: Not found error.", e.getMessage());

    }

    @ExceptionHandler(DojoErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse processDojoErrorException(RuntimeException e) {
        return createErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR, "DojoError: An internal server error occurred.", e.getMessage());

    }

    private ErrorResponse createErrorDTO(HttpStatus status, String message, String info) {
        return ErrorResponse.builder()
                .code(status.value())
                .info(info)
                .message(message)
                .build();

    }

}
