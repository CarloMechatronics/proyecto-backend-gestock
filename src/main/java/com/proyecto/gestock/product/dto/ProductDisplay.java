package com.proyecto.gestock.product.dto;

import com.proyecto.gestock.category.domain.Category;

import java.math.BigDecimal;

public interface ProductDisplay {
    Long getId();
    String getName();
    String getSummary();
    BigDecimal getPrice();
    Category getCategory();
}
