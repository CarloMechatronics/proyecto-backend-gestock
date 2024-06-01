package com.proyecto.gestock.brand.application;

import com.proyecto.gestock.brand.domain.Brand;
import com.proyecto.gestock.brand.domain.BrandService;
import com.proyecto.gestock.brand.dto.BrandDisplayDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    //--------CUSTOMER--------//
    @GetMapping("/all")
    public ResponseEntity<List<BrandDisplayDto>> getAllBrandsDisplay() {
        return new ResponseEntity<>(brandService.findAllBrandsByActiveTrue(), HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<BrandDisplayDto>> getAllByNameContains(@RequestParam String namePart) {
        return new ResponseEntity<>(brandService.findAllByNameContains(namePart), HttpStatus.OK);
    }
}
