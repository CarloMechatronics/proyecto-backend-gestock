package com.proyecto.gestock.product.dto;

import com.proyecto.gestock.category.domain.Category;

import java.math.BigDecimal;

public interface ProductInfo {
    Long getId();
    String getName();
    String getSummary();
    String getDescription();
    BigDecimal getPrice();
    Category getCategory();
}
