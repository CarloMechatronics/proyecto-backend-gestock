package com.proyecto.gestock.customer.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CustomerCreateDto {
    @NotBlank
    @Size(min = 2, max = 50)
    private String name;
    @NotBlank
    @Email
    @Size(min = 8, max = 64)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email format")
    private String email;
    @NotBlank
    @Size(min = 8, max = 20)
    private String password;
}
