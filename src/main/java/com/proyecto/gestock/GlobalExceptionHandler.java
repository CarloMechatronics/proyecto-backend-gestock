package com.proyecto.gestock;

import com.proyecto.gestock.exceptions.ResourceNotFoundException;
import com.proyecto.gestock.exceptions.UnauthorizedOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException ex){return ex.getMessage();}

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UnauthorizedOperationException.class)
    public String handleUnauthorizedOperationException(UnauthorizedOperationException ex){return ex.getMessage();}

}
