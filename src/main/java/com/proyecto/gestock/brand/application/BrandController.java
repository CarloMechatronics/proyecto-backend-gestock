package com.proyecto.gestock.brand.application;

import com.proyecto.gestock.brand.domain.Brand;
import com.proyecto.gestock.brand.domain.BrandService;
import com.proyecto.gestock.brand.dto.BrandCreateDto;
import com.proyecto.gestock.brand.dto.BrandDisplayDto;
import com.proyecto.gestock.brand.dto.BrandUpdateDto;
import com.proyecto.gestock.product.domain.Product;
import com.proyecto.gestock.product.dto.ProductDisplayDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    //--------ADMIN--------//
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Brand>> getAllBrands() {
        return new ResponseEntity<>(brandService.findAllBrands(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable Long id) {
        return new ResponseEntity<>(brandService.findBrandById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all/active")
    public ResponseEntity<List<Brand>> getAllActiveBrands(@RequestParam Boolean active) {
        return new ResponseEntity<>(brandService.findAllBrandsbyActive(active), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all/{id}/products")
    public ResponseEntity<List<Product>> getAllBrandProducts(@PathVariable Long id) {
        return new ResponseEntity<>(brandService.findAllBrandProductsById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Brand> createBrand(@RequestBody BrandCreateDto brandCreateDto) {
        return new ResponseEntity<>(brandService.saveBrand(brandCreateDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/update")
    public ResponseEntity<Brand> updateBrand(@PathVariable Long id, @RequestBody BrandUpdateDto brandUpdateDto) {
        return new ResponseEntity<>(brandService.updateBrandById(id, brandUpdateDto), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{brandId}/add-product")
    public ResponseEntity<List<Product>> updateBrandProducts(@PathVariable Long brandId, @RequestParam("product-id") Long productId) {
        return new ResponseEntity<>(brandService.addBrandProductByIds(brandId, productId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<List<Brand>> deleteBrand(@PathVariable Long id) {
        return new ResponseEntity<>(brandService.deleteBrandById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{brandId}/delete-product")
    public ResponseEntity<List<Product>> deleteBrandProduct(@PathVariable Long brandId, @RequestParam("product-id") Long productId) {
        return new ResponseEntity<>(brandService.deleteBrandProductByIds(brandId, productId), HttpStatus.OK);
    }


    //--------ANYONE--------//
    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<List<BrandDisplayDto>> getAllActiveBrandsDisplay() {
        return new ResponseEntity<>(brandService.findAllActiveBrands(), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/name")
    public ResponseEntity<List<BrandDisplayDto>> getAllActiveBrandsByNameContains(@RequestParam("name") String namePart) {
        return new ResponseEntity<>(brandService.findAllActiveBrandsByNameContains(namePart), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{brandName}/products")
    public ResponseEntity<List<ProductDisplayDto>> getAllBrandProductsDisplayDto(@PathVariable String brandName) {
        return new ResponseEntity<>(brandService.findAllBrandProductsDisplayDto(brandName), HttpStatus.OK);
    }
}
