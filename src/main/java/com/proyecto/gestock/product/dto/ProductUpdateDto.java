package com.proyecto.gestock.product.dto;

import com.proyecto.gestock.category.domain.Category;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductUpdateDto {
    private String name;
    private String summary;
    private String description;
    private BigDecimal price;
    private Boolean available;
    private Category category;
}
