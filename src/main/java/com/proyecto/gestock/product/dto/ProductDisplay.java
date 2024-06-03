package com.proyecto.gestock.product.dto;

import com.proyecto.gestock.brand.domain.Brand;
import com.proyecto.gestock.category.domain.Category;

import java.math.BigDecimal;

public interface ProductDisplay {
    Long getId();
    String getName();
    BigDecimal getPrice();
    Brand getBrand();
    Category getCategory();
}
