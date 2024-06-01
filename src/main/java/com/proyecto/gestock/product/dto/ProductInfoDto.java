package com.proyecto.gestock.product.dto;

import com.proyecto.gestock.category.domain.Category;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductInfoDto {
    @NotNull(message = "ID cannot be null")
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 6, max = 60, message = "Name must be more than 6 and less than 60 characters")
    private String name;

    @NotBlank
    @Size(max = 255, message = "Summary must be less than 255 characters")
    private String summary;

    @NotBlank
    @Size(max = 1020, message = "Description must be less than 1020 characters")
    private String description;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be greater than 0.0")
    private BigDecimal price;

    @NotNull
    private Category category;
}
