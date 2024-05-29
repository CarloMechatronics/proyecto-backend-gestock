package com.proyecto.gestock.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> generalExceptionHandler(Exception ex, WebRequest request) {
        String errorMessage = String.format("Error: %s, Request Details: %s", ex.getMessage(), request.getDescription(false));
        // TODO: Agregar un body
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> resourceNotFoundExceptionHandler(ResourceNotFoundException ex, WebRequest request) {
        String errorMessage = String.format("Error: %s, Request Details: %s", ex.getMessage(), request.getDescription(false));
        // TODO: Agregar un body
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> UnauthorizedOperationExceptionHandler(Exception ex, WebRequest request) {
        String errorMessage = String.format("Error: %s, Request Details: %s", ex.getMessage(), request.getDescription(false));
        // TODO: Agregar un body
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }
}
