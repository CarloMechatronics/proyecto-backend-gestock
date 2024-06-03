package com.proyecto.gestock.product.dto;

import com.proyecto.gestock.brand.domain.Brand;
import com.proyecto.gestock.category.domain.Category;
import com.proyecto.gestock.constraints.nullablesizevalidator.NullableSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductCreateDto {
    @NullableSize(min = 2, max = 60)
    private String name;
    @NullableSize(max = 1020)
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Brand brand;
    private Category category;
}
