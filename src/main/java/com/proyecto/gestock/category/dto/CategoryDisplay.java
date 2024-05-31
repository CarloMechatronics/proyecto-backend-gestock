package com.proyecto.gestock.category.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CategoryDisplay {
    @NotNull
    private Long id;

    @NotBlank
    @Size(min = 3, max = 16)
    private String name;

    @NotEmpty
    private String imageUrl;
}
