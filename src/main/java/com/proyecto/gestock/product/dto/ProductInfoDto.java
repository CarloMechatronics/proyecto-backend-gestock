package com.proyecto.gestock.product.dto;

import com.proyecto.gestock.brand.domain.Brand;
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
public class ProductInfoDto {
    private Long id;
    private String name;
    private String summary;
    private String description;
    private BigDecimal price;
    private Brand brand;
    private Category category;
}
