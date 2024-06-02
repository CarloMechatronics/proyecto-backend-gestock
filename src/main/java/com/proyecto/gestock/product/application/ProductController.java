package com.proyecto.gestock.product.application;

import com.proyecto.gestock.product.domain.Product;
import com.proyecto.gestock.product.domain.ProductService;
import com.proyecto.gestock.product.dto.ProductDisplay;
import com.proyecto.gestock.product.dto.ProductDisplayDto;
import com.proyecto.gestock.product.dto.ProductInfoDto;
import com.proyecto.gestock.product.dto.ProductUpdateDto;
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
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.findProductById(id), HttpStatus.OK);
    }

    @GetMapping("/name-has")
    public ResponseEntity<List<Product>> getProductByNameContains(@RequestParam("name") String namePart) {
        return new ResponseEntity<>(productService.findAllProductsByNameContains(namePart), HttpStatus.OK);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductByPriceRange(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return new ResponseEntity<>(productService.findAllProductsByPriceRange(min, max), HttpStatus.OK);
    }

    @GetMapping("/stock-greater-than")
    public ResponseEntity<List<Product>> getProductsByStockGreaterThanEqual(@RequestParam Integer stock) {
        return new ResponseEntity<>(productService.findAllProductsByStockGreaterThanEqual(stock), HttpStatus.OK);
    }

    @GetMapping("/stock-less-than")
    public ResponseEntity<List<Product>> getProductsByStockLessThanEqual(@RequestParam Integer stock) {
        return new ResponseEntity<>(productService.findAllProductsByStockLessThanEqual(stock), HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Product>> getProductsByAvailable(@RequestParam Boolean available) {
        return new ResponseEntity<>(productService.findAllProductsByAvailable(available), HttpStatus.OK);
    }

    @GetMapping("/category-id/{id}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long id) {
        return new ResponseEntity<>(productService.findAllProductsByCategoryId(id), HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateDto product) {
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

    @GetMapping("/category-name/{name}")
    public ResponseEntity<List<ProductDisplayDto>> getValidProductsByCategoryName(@PathVariable String name) {
        return new ResponseEntity<>(productService.findAllValidProductsByCategoryName(name), HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<ProductUpdateDto> createProduct(@RequestBody ProductUpdateDto productUpdateDto) {
//        ProductUpdateDto createdProduct = productService.createProduct(productUpdateDto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ProductUpdateDto> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateDto productUpdateDto) {
//        ProductUpdateDto updatedProduct = productService.updateProduct(id, productUpdateDto);
//        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
//        productService.deleteById(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}
