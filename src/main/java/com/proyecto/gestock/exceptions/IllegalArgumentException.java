package com.proyecto.gestock.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Unavailable action")
public class IllegalArgumentException extends RuntimeException {
    public IllegalArgumentException(String message) {super(message);}
}
