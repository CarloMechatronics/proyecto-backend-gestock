package com.proyecto.gestock.product.dto;

import com.proyecto.gestock.category.domain.Category;

import java.math.BigDecimal;

public interface ProductDisplay {
    Long getId();
    String getName();
    String getDescription();
    BigDecimal getPrice();
    Boolean isAvailable();
    Category getCategory();
}
