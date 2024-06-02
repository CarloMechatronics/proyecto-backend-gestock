package com.proyecto.gestock.category.dto;

import com.proyecto.gestock.constraints.nullablesizevalidator.NullableSize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CategoryUpdateDto {
    @NullableSize(min = 2, max = 16)
    private String name;
    private String imageUrl;
}
