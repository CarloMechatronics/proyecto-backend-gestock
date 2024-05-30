package com.proyecto.gestock.category.domain;

import com.proyecto.gestock.category.infrastructure.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).get();
    }

    public Category save(Category product) {
        return categoryRepository.save(product);
    }

    public Category update(Category product) {
        return null;
    }

    public void deleteById(Long id) {

    }
}
