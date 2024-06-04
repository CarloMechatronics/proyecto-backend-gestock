package com.proyecto.gestock.category.domain;

import com.proyecto.gestock.brand.domain.Brand;
import com.proyecto.gestock.brand.dto.BrandUpdateDto;
import com.proyecto.gestock.category.dto.CategoryCreateDto;
import com.proyecto.gestock.category.dto.CategoryDisplay;
import com.proyecto.gestock.category.dto.CategoryDisplayDto;
import com.proyecto.gestock.category.dto.CategoryUpdateDto;
import com.proyecto.gestock.category.infrastructure.CategoryRepository;
import com.proyecto.gestock.exceptions.ResourceNotFoundException;
import com.proyecto.gestock.product.domain.Product;
import com.proyecto.gestock.product.dto.ProductDisplayDto;
import com.proyecto.gestock.product.infrastructure.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper nonNullMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository, @Qualifier("nonNullMapper") ModelMapper nonNullMapper, @Qualifier("modelMapper") ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.nonNullMapper = nonNullMapper;
        this.modelMapper = modelMapper;
    }

    //--------ADMIN--------//
    //----GET----//
    @Transactional(readOnly = true)
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public List<Product> findAllCategoryProductsById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with name " + id + " not found"));

        return category.getProducts();
    }

    //----POST----//
    @Transactional
    public Category saveCategory(CategoryCreateDto categoryCreateDto) {
        return categoryRepository.save(nonNullMapper.map(categoryCreateDto, Category.class));
    }

    //----PATCH----//
    @Transactional
    public Category updateCategoryById(Long id, CategoryUpdateDto categoryUpdateDto) {
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
    @Transactional
    public List<Category> deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category with id " + id + " not found");
        }

        categoryRepository.deleteById(id);

        return categoryRepository.findAll();
    }

    @Transactional
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


    //--------ANYONE--------//
    @Transactional(readOnly = true)
    public List<CategoryDisplayDto> findAllCategoriesByIdNotNull() {
        List<CategoryDisplay> categoryDisplayList = categoryRepository.findAllByIdNotNull();

        return categoryDisplayList.stream()
                .map(categoryDisplay -> modelMapper.map(categoryDisplay, CategoryDisplayDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CategoryDisplayDto> findAllCategoriesByNameContains(String namePart) {
        List<CategoryDisplay> categoryDisplayList = categoryRepository.findAllByNameContains(namePart);

        return categoryDisplayList.stream()
                .map(categoryDisplay -> modelMapper.map(categoryDisplay, CategoryDisplayDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDisplayDto> findAllCategoryProductsByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category with name " + name + " not found"));
        List<Product> productList = category.getProducts();

        return productList.stream()
                .map(product -> modelMapper.map(product, ProductDisplayDto.class))
                .collect(Collectors.toList());
    }
}
