package com.proyecto.gestock.authentication.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SignInDTO {
    private String email;
    private String password;
    private String name;
    private LocalDate registrationDate;
}
