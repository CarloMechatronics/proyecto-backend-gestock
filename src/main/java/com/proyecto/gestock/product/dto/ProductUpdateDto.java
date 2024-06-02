package com.proyecto.gestock.product.dto;

import com.proyecto.gestock.category.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
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
