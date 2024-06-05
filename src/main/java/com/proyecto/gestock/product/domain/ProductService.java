package com.proyecto.gestock.product.domain;

import com.proyecto.gestock.authentication.utils.Authorization;
import com.proyecto.gestock.brand.domain.Brand;
import com.proyecto.gestock.brand.infrastructure.BrandRepository;
import com.proyecto.gestock.category.domain.Category;
import com.proyecto.gestock.category.infrastructure.CategoryRepository;
import com.proyecto.gestock.exceptions.ResourceNotFoundException;
import com.proyecto.gestock.exceptions.UnauthorizedOperationException;
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
    private final Authorization authorization;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper, @Qualifier("nonNullMapper") ModelMapper nonNullMapper, BrandRepository brandRepository, CategoryRepository categoryRepository, Authorization authorization) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.nonNullMapper = nonNullMapper;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.authorization = authorization;
    }

    //-------ADMIN--------//
    //----GET----//
    @Transactional(readOnly = true)
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product findProductById(Long id) {
        Product product =  productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAllProductsByNameContains(String namePart) {
        List<Product> products = productRepository.findAllByNameContains(namePart);
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
        return products;
    }

    @Transactional(readOnly = true)
    public List<Product> findAllProductsByPriceRange(BigDecimal min, BigDecimal max) {
        List<Product> products = productRepository.findAllByPriceGreaterThanEqualAndPriceLessThanEqual(min, max);
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
        return products;
    }

    @Transactional(readOnly = true)
    public List<Product> findAllProductsByStockGreaterThanEqual(Integer stock) {
        List<Product> products = productRepository.findAllByStockGreaterThanEqual(stock);
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
        return products;
    }

    @Transactional(readOnly = true)
    public List<Product> findAllProductsByStockLessThanEqual(Integer stock) {
        List<Product> products =  productRepository.findAllByStockLessThanEqual(stock);
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
        return products;
    }

    //----POST----//
    @Transactional
    public Product saveProduct(ProductCreateDto productCreateDto) {
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
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
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        nonNullMapper.map(productUpdateDto, existingProduct);

        return productRepository.save(existingProduct);
    }


    //----DELETE----//
    @Transactional
    public void deleteProductById(Long id) {
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
        if (!productRepository.existsById(id))
            throw new ResourceNotFoundException("Product with id " + id + " not found");
        productRepository.deleteById(id);
    }


    //--------ANYONE--------//
    @Transactional(readOnly = true)
    public ProductInfoDto findValidProductByName(String name) {
        ProductInfo productInfo = productRepository.findByNameAndStockGreaterThanEqual(name, 0).orElseThrow(() -> new ResourceNotFoundException("Product with name '" + name + "' not found"));

        return modelMapper.map(productInfo, ProductInfoDto.class);
    }

    @Transactional(readOnly = true)
    public List<ProductDisplayDto> findAllValidProductsByNameContains(String namePart) {
        List<ProductDisplay> productDisplayList = productRepository
                .findAllByNameContainsAndStockGreaterThanEqual(namePart, 0);

        return productDisplayList.stream()
                .map(productDisplay -> modelMapper.map(productDisplay, ProductDisplayDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDisplayDto> findAllValidProductByPriceRange(String namePart, BigDecimal min, BigDecimal max) {
        List<ProductDisplay> productDisplayList = productRepository
                .findAllByNameContainsAndPriceGreaterThanEqualAndPriceLessThanEqualAndStockGreaterThanEqual(namePart, min, max, 0);

        return productDisplayList.stream()
                .map(productDisplay -> modelMapper.map(productDisplay, ProductDisplayDto.class))
                .collect(Collectors.toList());
    }
}
