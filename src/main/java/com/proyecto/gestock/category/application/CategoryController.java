package com.proyecto.gestock.category.application;

import com.proyecto.gestock.category.domain.Category;
import com.proyecto.gestock.category.domain.CategoryService;
import com.proyecto.gestock.category.dto.CategoryCreateDto;
import com.proyecto.gestock.category.dto.CategoryDisplayDto;
import com.proyecto.gestock.category.dto.CategoryUpdateDto;
import com.proyecto.gestock.product.domain.Product;
import com.proyecto.gestock.product.dto.ProductDisplayDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(categoryService.findAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return new ResponseEntity<>(categoryService.findCategoryById(id), HttpStatus.OK);
    }

    @GetMapping("/all/{id}/products")
    public ResponseEntity<List<Product>> getAllCategoryProducts(@PathVariable Long id) {
        return new ResponseEntity<>(categoryService.findAllCategoryProductsById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryCreateDto categoryCreateDto) {
        return new ResponseEntity<>(categoryService.saveCategory(categoryCreateDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody CategoryUpdateDto categoryUpdateDto) {
        return new ResponseEntity<>(categoryService.updateCategoryById(id, categoryUpdateDto), HttpStatus.OK);
    }

    @PatchMapping("/{categoryId}/add-product")
    public ResponseEntity<List<Product>> updateBrandProducts(@PathVariable Long categoryId, @RequestParam("product-id") Long productId) {
        return new ResponseEntity<>(categoryService.addCategoryProductByIds(categoryId, productId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<List<Category>> deleteCategory(@PathVariable Long id) {
        return new ResponseEntity<>(categoryService.deleteCategoryById(id), HttpStatus.OK);
    }


    @DeleteMapping("/{categoryId}/delete-product")
    public ResponseEntity<List<Product>> deleteBrandProduct(@PathVariable Long categoryId, @RequestParam("product-id") Long productId) {
        return new ResponseEntity<>(categoryService.deleteCategoryProductByIds(categoryId, productId), HttpStatus.OK);
    }


    //--------ANYONE--------//
    @GetMapping
    public ResponseEntity<List<CategoryDisplayDto>> getAllCategoriesDisplayDto() {
        return new ResponseEntity<>(categoryService.findAllCategoriesByIdNotNull(), HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<CategoryDisplayDto>> getAllCategoriesByNameContains(@RequestParam("name") String namePart) {
        return new ResponseEntity<>(categoryService.findAllCategoriesByNameContains(namePart), HttpStatus.OK);
    }

    @GetMapping("/{categoryName}/products")
    public ResponseEntity<List<ProductDisplayDto>> getAllCategoryProductsDisplayDto(@PathVariable String categoryName) {
        return new ResponseEntity<>(categoryService.findAllCategoryProductsByName(categoryName), HttpStatus.OK);
    }
}
