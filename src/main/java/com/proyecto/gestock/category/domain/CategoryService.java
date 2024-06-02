package com.proyecto.gestock.category.domain;

import com.proyecto.gestock.brand.domain.Brand;
import com.proyecto.gestock.brand.dto.BrandUpdateDto;
import com.proyecto.gestock.category.dto.CategoryUpdateDto;
import com.proyecto.gestock.category.infrastructure.CategoryRepository;
import com.proyecto.gestock.exceptions.ResourceNotFoundException;
import com.proyecto.gestock.product.domain.Product;
import com.proyecto.gestock.product.infrastructure.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper nonNullMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository, @Qualifier("nonNullMapper") ModelMapper nonNullMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.nonNullMapper = nonNullMapper;
    }

    //--------ADMIN--------//
    //----GET----//
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
    }

    public List<Product> findAllCategoryProductsById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with name " + id + " not found"));

        return category.getProducts();
    }

    //----POSTS----//
    @Transactional
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    //----PATCH----//
    @Transactional
    public Category updateBrandById(Long id, CategoryUpdateDto categoryUpdateDto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
        nonNullMapper.map(categoryUpdateDto, existingCategory);

        return categoryRepository.save(existingCategory);
    }

    @Transactional
    public List<Product> addCategoryProductByIds(Long categoryId, Long productId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + categoryId + " not found"));

        category.getProducts().add(productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + productId + " not found")));

        categoryRepository.save(category);

        return category.getProducts();
    }

    //----DELETE----//
    public List<Category> deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category with id " + id + " not found");
        }

        categoryRepository.deleteById(id);

        return categoryRepository.findAll();
    }

    public List<Product> deleteCategoryProductByIds(Long categoryId, Long productId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + categoryId + " not found"));

        Product product = category.getProducts().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + productId + " not found in category with id " + categoryId));

        productRepository.deleteById(productId);
        category.getProducts().remove(product);

        categoryRepository.save(category);

        return category.getProducts();
    }
}
