package com.proyecto.gestock.product.application;

import com.proyecto.gestock.product.domain.Product;
import com.proyecto.gestock.product.domain.ProductService;
import com.proyecto.gestock.product.dto.ProductDisplayDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/stock-greater")
    public ResponseEntity<List<Product>> getProductsByStockGreaterThanEqual(@RequestParam Integer stock) {
        return new ResponseEntity<>(productService.findAllProductsByStockGreaterThanEqual(stock), HttpStatus.OK);
    }

    @GetMapping("/stock-less")
    public ResponseEntity<List<Product>> getProductsByStockLessThanEqual(@RequestParam Integer stock) {
        return new ResponseEntity<>(productService.findAllProductsByStockLessThanEqual(stock), HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long id) {
        return new ResponseEntity<>(productService.findAllProductsByCategoryId(id), HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Product>> getProductsByAvailable(@RequestParam Boolean available) {
        return new ResponseEntity<>(productService.findAllProductsByAvailable(available), HttpStatus.OK);
    }


    //--------CUSTOMER--------//
    @GetMapping("/name")
    public ResponseEntity<ProductDisplayDto> getValidProductByName(@RequestParam String name) {
        return new ResponseEntity<>(productService.findValidProductByName(name), HttpStatus.OK);
    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<List<ProductDisplayDto>> getValidProductsByCategoryName(@PathVariable String categoryName) {
        return new ResponseEntity<>(productService.findAllValidProductsByCategoryName(categoryName), HttpStatus.OK);
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
