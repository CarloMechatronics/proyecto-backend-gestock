package com.proyecto.gestock.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductListingDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String brandName;
}
