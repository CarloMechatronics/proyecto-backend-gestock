package com.proyecto.gestock.authentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignInDTO {
    @NotBlank
    @Email
    private String email;

    @NotNull
    @Size(min = 3)
    private String password;

    @NotBlank
    @Size(min = 5, max = 80)
    private String name;

    @NotNull
    private LocalDate registrationDate;
}
