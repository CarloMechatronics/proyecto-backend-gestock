package com.proyecto.gestock.customer.dto;

import com.proyecto.gestock.constraints.nullablesizevalidator.NullableSize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerUpdateDto {
    @NullableSize(min = 5, max = 80)
    String name;
    @Email
    @NullableSize(min = 8, max = 64)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email format")
    String email;
    @NullableSize(min = 8, max = 20)
    String password;
}
