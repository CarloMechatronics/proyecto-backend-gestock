package com.proyecto.gestock.brand.application;

import com.proyecto.gestock.brand.domain.Brand;
import com.proyecto.gestock.brand.domain.BrandService;
import com.proyecto.gestock.brand.dto.BrandDisplayDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {
    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    //--------ADMIN--------//
    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {
        return new ResponseEntity<>(brandService.findAllBrands(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable Long id) {
        return new ResponseEntity<>(brandService.findBrandById(id), HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Brand>> getBrandById(@RequestParam Boolean active) {
        return new ResponseEntity<>(brandService.findAllBrandsbyActive(active), HttpStatus.OK);
    }

    //--------CUSTOMER--------//
    @GetMapping("/name")
    public ResponseEntity<List<BrandDisplayDto>> getAllValidBrandsByNameContains(@RequestParam String namePart) {
        return new ResponseEntity<>(brandService.findAllValidBrandsByNameContains(namePart), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BrandDisplayDto>> getAllBrandsDisplay() {
        return new ResponseEntity<>(brandService.findAllValidBrands(), HttpStatus.OK);
    }
}
