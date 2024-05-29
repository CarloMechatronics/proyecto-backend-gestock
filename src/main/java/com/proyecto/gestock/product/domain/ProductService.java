package com.proyecto.gestock.product.domain;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(Long id);
    Product save(Product product);
    Product update(Long id, Product product);
    void deleteById(Long id);
    boolean existsById(Long id);
}
