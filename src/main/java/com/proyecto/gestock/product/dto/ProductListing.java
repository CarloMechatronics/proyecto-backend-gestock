package com.proyecto.gestock.product.dto;

import java.math.BigDecimal;

public interface ProductListing {
    Long getId();
    String getName();
    BigDecimal getPrice();
    String getBrandName();
}
