package com.proyecto.gestock.product.application;

import com.proyecto.gestock.product.domain.Product;
import com.proyecto.gestock.product.domain.ProductService;
import com.proyecto.gestock.product.dto.ProductDisplayDto;
import com.proyecto.gestock.product.dto.ProductInfoDto;
import com.proyecto.gestock.product.dto.ProductUpdateDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Valid
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    //--------ADMIN--------//
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.findProductById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all/name")
    public ResponseEntity<List<Product>> getProductByNameContains(@RequestParam("name") String namePart) {
        return new ResponseEntity<>(productService.findAllProductsByNameContains(namePart), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all/price-range")
    public ResponseEntity<List<Product>> getProductByPriceRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return new ResponseEntity<>(productService.findAllProductsByPriceRange(min, max), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all/stock-greater-than")
    public ResponseEntity<List<Product>> getProductsByStockGreaterThanEqual(@RequestParam Integer stock) {
        return new ResponseEntity<>(productService.findAllProductsByStockGreaterThanEqual(stock), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all/stock-less-than")
    public ResponseEntity<List<Product>> getProductsByStockLessThanEqual(@RequestParam Integer stock) {
        return new ResponseEntity<>(productService.findAllProductsByStockLessThanEqual(stock), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateDto productUpdateDto) {
        return new ResponseEntity<>(productService.updateProductById(id, productUpdateDto), HttpStatus.OK);
    }


    //--------ANYONE--------//
    @PreAuthorize("permitAll()")
    @GetMapping("/{name}")
    public ResponseEntity<ProductInfoDto> getValidProductByName(@PathVariable String name) {
        return new ResponseEntity<>(productService.findValidProductByName(name), HttpStatus.OK);
    }
    @PreAuthorize("permitAll()")
    @GetMapping("/name")
    public ResponseEntity<List<ProductDisplayDto>> getAllValidProductsByNameContains(@RequestParam("name") String namePart) {
        return new ResponseEntity<>(productService.findAllValidProductsByNameContains(namePart), HttpStatus.OK);
    }
    @PreAuthorize("permitAll()")
    @GetMapping("/price")
    public ResponseEntity<List<ProductDisplayDto>> getAllValidProductsByNameContainsAndPriceRange(@RequestParam("name") String namePart, @RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return new ResponseEntity<>(productService.findAllValidProductByPriceRange(namePart, min, max), HttpStatus.OK);
    }
}
