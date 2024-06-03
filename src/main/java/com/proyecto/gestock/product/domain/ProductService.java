package com.proyecto.gestock.product.domain;

import com.proyecto.gestock.brand.domain.Brand;
import com.proyecto.gestock.brand.infrastructure.BrandRepository;
import com.proyecto.gestock.category.domain.Category;
import com.proyecto.gestock.category.infrastructure.CategoryRepository;
import com.proyecto.gestock.exceptions.ResourceNotFoundException;
import com.proyecto.gestock.product.dto.*;
import com.proyecto.gestock.product.infrastructure.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ModelMapper nonNullMapper;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper, @Qualifier("nonNullMapper") ModelMapper nonNullMapper, BrandRepository brandRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.nonNullMapper = nonNullMapper;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }

    //-------ADMIN--------//
    //----GET----//
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
    }

    public List<Product> findAllProductsByNameContains(String namePart) {
        return productRepository.findAllByNameContains(namePart);
    }

    public List<Product> findAllProductsByPriceRange(BigDecimal min, BigDecimal max) {
        return productRepository.findAllByPriceGreaterThanEqualAndPriceLessThanEqual(min, max);
    }

    public List<Product> findAllProductsByStockGreaterThanEqual(Integer stock) {
        return productRepository.findAllByStockGreaterThanEqual(stock);
    }

    public List<Product> findAllProductsByStockLessThanEqual(Integer stock) {
        return productRepository.findAllByStockLessThanEqual(stock);
    }

    //----POST----//
    @Transactional
    public Product saveProduct(ProductCreateDto productCreateDto) {
        if (productCreateDto.getBrand() == null)
            throw new IllegalArgumentException("Category cannot be null");

        Brand brand;
        if (productCreateDto.getBrand().getId() != null) {
            brand = brandRepository.findById(productCreateDto.getBrand().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        } else if (productCreateDto.getBrand().getName() != null && !productCreateDto.getBrand().getName().isEmpty()) {
            brand = brandRepository.findByNameAndActiveTrue(productCreateDto.getBrand().getName())
                    .orElseThrow(() -> new IllegalArgumentException("Brand not found"));
        } else throw new IllegalArgumentException("Brand id or name must be provided");

        Category category;
        if (productCreateDto.getCategory().getId() != null) {
            category = categoryRepository.findById(productCreateDto.getCategory().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        } else if (productCreateDto.getCategory().getName() != null && !productCreateDto.getCategory().getName().isEmpty()) {
            category = categoryRepository.findByName(productCreateDto.getCategory().getName())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        } else throw new IllegalArgumentException("Category id or name must be provided");

        Product newProduct = nonNullMapper.map(productCreateDto, Product.class);
        newProduct.setBrand(brand);
        newProduct.setCategory(category);

        return productRepository.save(newProduct);
    }

    //----PATCH----//
    @Transactional
    public Product updateProductById(Long id, ProductUpdateDto productUpdateDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        nonNullMapper.map(productUpdateDto, existingProduct);

        return productRepository.save(existingProduct);
    }


    //----DELETE----//
    @Transactional
    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id))
            throw new ResourceNotFoundException("Product with id " + id + " not found");

        productRepository.deleteById(id);
    }


    //--------ANYONE--------//
    public ProductInfoDto findValidProductByName(String name) {
        ProductInfo productInfo = productRepository.findByNameAndStockGreaterThanEqual(name, 0)
                .orElseThrow(() -> new ResourceNotFoundException("Product with name '" + name + "' not found"));

        return modelMapper.map(productInfo, ProductInfoDto.class);
    }

    public List<ProductDisplayDto> findAllValidProductsByNameContains(String namePart) {
        List<ProductDisplay> productDisplayList = productRepository
                .findAllByNameContainsAndStockGreaterThanEqual(namePart, 0);

        return productDisplayList.stream()
                .map(productDisplay -> modelMapper.map(productDisplay, ProductDisplayDto.class))
                .collect(Collectors.toList());
    }

    public List<ProductDisplayDto> findAllValidProductByPriceRange(String namePart, BigDecimal min, BigDecimal max) {
        List<ProductDisplay> productDisplayList = productRepository
                .findAllByNameContainsAndPriceGreaterThanEqualAndPriceLessThanEqualAndStockGreaterThanEqual(namePart, min, max, 0);

        return productDisplayList.stream()
                .map(productDisplay -> modelMapper.map(productDisplay, ProductDisplayDto.class))
                .collect(Collectors.toList());
    }
}
