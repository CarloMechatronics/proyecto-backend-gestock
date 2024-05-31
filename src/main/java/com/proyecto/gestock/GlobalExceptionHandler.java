package com.proyecto.gestock;

import com.proyecto.gestock.exceptions.ErrorBody;
import com.proyecto.gestock.exceptions.IllegalArgumentException;
import com.proyecto.gestock.exceptions.ResourceNotFoundException;
import com.proyecto.gestock.exceptions.UnauthorizedOperationException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorBody> generalExceptionHandler(Exception exception, WebRequest request) {
        ErrorBody errorBody = new ErrorBody(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                request.getDescription(false).replace("uri=", ""),
                exception.getMessage(),
                request.getDescription(false)

        );
        return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorBody> resourceNotFoundExceptionHandler(ResourceNotFoundException exception, WebRequest request) {
        ErrorBody errorBody = new ErrorBody(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                request.getDescription(false).replace("uri=", ""),
                exception.getMessage(),
                request.getDescription(true)
        );

        return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorBody> methodArgumentNotValidExceptionHandler(ConstraintViolationException exception, WebRequest request) {
        ErrorBody errorBody = new ErrorBody(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                request.getDescription(false).replace("uri=", ""),
                exception.getMessage(),
                request.getDescription(true)
        );

        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedOperationException.class)
    public ResponseEntity<ErrorBody> unauthorizedOperationExceptionHandler(UnauthorizedOperationException exception, WebRequest request) {
        ErrorBody errorBody = new ErrorBody(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                request.getDescription(false).replace("uri=", ""),
                exception.getMessage(),
                request.getDescription(true)
        );

        return new ResponseEntity<>(errorBody, HttpStatus.UNAUTHORIZED); // UNAUTHORIZED O FORBIDDEN
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorBody> illegalArgumentExceptionHandler(IllegalArgumentException exception, WebRequest request) {
        ErrorBody errorBody = new ErrorBody(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                request.getDescription(false).replace("uri=", ""),
                exception.getMessage(),
                request.getDescription(true)
        );

        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

}
