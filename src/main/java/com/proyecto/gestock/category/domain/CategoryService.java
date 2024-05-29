package com.proyecto.gestock.category.domain;

import com.proyecto.gestock.category.infrastructure.CategoryRepository;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    Category save(Category product);
    Category update(Category product);
    void deleteById(Long id);
}
