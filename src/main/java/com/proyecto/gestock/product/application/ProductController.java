package com.proyecto.gestock.product.application;

import com.proyecto.gestock.product.domain.Product;
import com.proyecto.gestock.product.domain.ProductService;
import com.proyecto.gestock.product.dto.ProductDisplayDto;
import com.proyecto.gestock.product.dto.ProductInfoDto;
import com.proyecto.gestock.product.dto.ProductRequestDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Valid
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    //--------ADMIN--------//
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.findProductById(id), HttpStatus.OK);
    }

    @GetMapping("/all/name")
    public ResponseEntity<List<Product>> getProductByNameContains(@RequestParam("name") String namePart) {
        return new ResponseEntity<>(productService.findAllProductsByNameContains(namePart), HttpStatus.OK);
    }

    @GetMapping("/all/price-range")
    public ResponseEntity<List<Product>> getProductByPriceRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return new ResponseEntity<>(productService.findAllProductsByPriceRange(min, max), HttpStatus.OK);
    }

    @GetMapping("/all/stock-greater-than")
    public ResponseEntity<List<Product>> getProductsByStockGreaterThanEqual(@RequestParam Integer stock) {
        return new ResponseEntity<>(productService.findAllProductsByStockGreaterThanEqual(stock), HttpStatus.OK);
    }

    @GetMapping("/all/stock-less-than")
    public ResponseEntity<List<Product>> getProductsByStockLessThanEqual(@RequestParam Integer stock) {
        return new ResponseEntity<>(productService.findAllProductsByStockLessThanEqual(stock), HttpStatus.OK);
    }

    @GetMapping("/all/available")
    public ResponseEntity<List<Product>> getProductsByAvailable(@RequestParam Boolean available) {
        return new ResponseEntity<>(productService.findAllProductsByAvailable(available), HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto product) {
        return new ResponseEntity<>(productService.updateProductById(id, product), HttpStatus.OK);
    }

    //--------CUSTOMER--------//
    @GetMapping("/{name}")
    public ResponseEntity<ProductInfoDto> getValidProductByName(@PathVariable String name) {
        return new ResponseEntity<>(productService.findValidProductByName(name), HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<ProductDisplayDto>> getAllValidProductsByNameContains(@RequestParam("name") String namePart) {
        return new ResponseEntity<>(productService.findAllValidProductsByNameContains(namePart), HttpStatus.OK);
    }

    @GetMapping("/price")
    public ResponseEntity<List<ProductDisplayDto>> getAllValidProductsByPriceRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return new ResponseEntity<>(productService.findAllValidProductByPriceRange(min, max), HttpStatus.OK);
    }
}
