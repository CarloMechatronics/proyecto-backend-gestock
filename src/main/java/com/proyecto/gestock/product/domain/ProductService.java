package com.proyecto.gestock.product.domain;

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

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper, @Qualifier("nonNullMapper") ModelMapper nonNullMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.nonNullMapper = nonNullMapper;
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

    public List<Product> findAllProductsByAvailable(Boolean available) {
        return productRepository.findAllByAvailable(available);
    }

    //----POST----//

    //----PATCH----//
    @Transactional
    public Product updateProductById(Long id, ProductRequestDto product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        nonNullMapper.map(product, existingProduct);

        return productRepository.save(existingProduct);
    }

    @Transactional
    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id))
            throw new ResourceNotFoundException("Product with id " + id + " not found");

        productRepository.deleteById(id);
    }

    //----DELETE----//


    //--------CUSTOMER--------//
    public ProductInfoDto findValidProductByName(String name) {
        ProductInfo productInfo = productRepository.findByNameAndAvailableTrueAndStockGreaterThan(name, 0)
                .orElseThrow(() -> new ResourceNotFoundException("Product with name '" + name + "' not found"));

        return modelMapper.map(productInfo, ProductInfoDto.class);
    }

    public List<ProductDisplayDto> findAllValidProductsByNameContains(String namePart) {
        List<ProductDisplay> productDisplayList = productRepository
                .findAllByNameContainsAndAvailableTrueAndStockGreaterThan(namePart, 0);

        return productDisplayList.stream()
                .map(productDisplay -> modelMapper.map(productDisplay, ProductDisplayDto.class))
                .collect(Collectors.toList());
    }

    public List<ProductDisplayDto> findAllValidProductByPriceRange(BigDecimal min, BigDecimal max) {
        List<ProductDisplay> productDisplayList = productRepository
                .findAllByPriceGreaterThanEqualAndPriceLessThanEqualAndAvailableTrueAndStockGreaterThan(min, max, 0);

        return productDisplayList.stream()
                .map(productDisplay -> modelMapper.map(productDisplay, ProductDisplayDto.class))
                .collect(Collectors.toList());
    }
}
