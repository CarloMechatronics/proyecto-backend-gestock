package com.proyecto.gestock.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ErrorBody {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;
    private String message;
    private String details;
}
