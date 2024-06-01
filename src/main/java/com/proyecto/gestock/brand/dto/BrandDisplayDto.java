package com.proyecto.gestock.brand.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BrandDisplayDto {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 20)
    private String name;

    @NotEmpty(message = "Logo cannot be empty")
    private String logo;
}
