package com.proyecto.gestock.brand.dto;

import com.proyecto.gestock.constraints.nullablesizevalidator.NullableSize;
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
public class BrandUpdateDto {
    @NullableSize(min= 2, max = 20)
    private String name;

    private String logo;

    private Boolean active;
}
