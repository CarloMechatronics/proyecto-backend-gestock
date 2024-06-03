package com.proyecto.gestock.product.dto;

import com.proyecto.gestock.brand.domain.Brand;

import java.math.BigDecimal;

public interface ProductListing {
    Long getId();
    String getName();
    BigDecimal getPrice();
    Brand getBrand();
}
