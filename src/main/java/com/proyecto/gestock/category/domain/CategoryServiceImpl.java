package com.proyecto.gestock.category.domain;

import com.proyecto.gestock.category.infrastructure.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public Category save(Category product) {
        return categoryRepository.save(product);
    }

    @Override
    public Category update(Category product) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
