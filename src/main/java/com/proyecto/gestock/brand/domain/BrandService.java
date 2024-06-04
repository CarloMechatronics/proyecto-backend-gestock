package com.proyecto.gestock.brand.domain;

import com.proyecto.gestock.brand.dto.BrandCreateDto;
import com.proyecto.gestock.brand.dto.BrandDisplay;
import com.proyecto.gestock.brand.dto.BrandDisplayDto;
import com.proyecto.gestock.brand.dto.BrandUpdateDto;
import com.proyecto.gestock.brand.infrastructure.BrandRepository;
import com.proyecto.gestock.exceptions.ResourceNotFoundException;
import com.proyecto.gestock.product.domain.Product;
import com.proyecto.gestock.product.dto.ProductDisplayDto;
import com.proyecto.gestock.product.infrastructure.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandService {
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ModelMapper nonNullMapper;

    @Autowired
    public BrandService(BrandRepository brandRepository, ProductRepository productRepository, ModelMapper modelMapper, @Qualifier("nonNullMapper") ModelMapper nonNullMapper) {
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.nonNullMapper = nonNullMapper;
    }

    //--------ADMIN--------//
    //----GET----//
    @Transactional(readOnly = true)
    public List<Brand> findAllBrands() {
        return brandRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Brand findBrandById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public List<Brand> findAllBrandsbyActive(Boolean active) {
        return brandRepository.findAllByActive(active);
    }

    @Transactional(readOnly = true)
    public List<Product> findAllBrandProductsById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with name " + id + " not found"));

        return brand.getProducts();
    }

    //----POST----//
    @Transactional
    public Brand saveBrand(BrandCreateDto brandCreateDto) {
        return brandRepository.save(nonNullMapper.map(brandCreateDto, Brand.class));
    }

    //----PATCH----//
    @Transactional
    public Brand updateBrandById(Long id, BrandUpdateDto brandUpdateDto) {
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand with id " + id + " not found"));
        nonNullMapper.map(brandUpdateDto, existingBrand);

        return brandRepository.save(existingBrand);
    }

    @Transactional
    public List<Product> addBrandProductByIds(Long brandId, Long productId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand with id " + brandId + " not found"));

        brand.getProducts().add(productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + productId + " not found")));

        brandRepository.save(brand);

        return brand.getProducts();
    }

    //----DELETE----//
    @Transactional
    public List<Brand> deleteBrandById(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new ResourceNotFoundException("Brand with id " + id + " not found");
        }

        brandRepository.deleteById(id);

        return brandRepository.findAll();
    }

    @Transactional
    public List<Product> deleteBrandProductByIds(Long brandId, Long productId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand with id " + brandId + " not found"));

        Product product = brand.getProducts().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + productId + " not found in brand with id " + brandId));

        productRepository.deleteById(productId);
        brand.getProducts().remove(product);

        brandRepository.save(brand);

        return brand.getProducts();
    }


    //--------ANYONE--------//
    @Transactional(readOnly = true)
    public List<BrandDisplayDto> findAllActiveBrands() {
        List<BrandDisplay> brandDisplayList = brandRepository.findAllByActiveTrue();
        return brandDisplayList.stream()
                .map(brandDisplay -> modelMapper.map(brandDisplay, BrandDisplayDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BrandDisplayDto> findAllActiveBrandsByNameContains(String namePart) {
        List<BrandDisplay> brandDisplayList = brandRepository.findAllByNameContainsAndActiveTrue(namePart);
        return brandDisplayList.stream()
                .map(brandDisplay -> modelMapper.map(brandDisplay, BrandDisplayDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDisplayDto> findAllBrandProductsDisplayDto(String name) {
        Brand brand = brandRepository.findByNameAndActiveTrue(name)
                .orElseThrow(() -> new ResourceNotFoundException("Brand with name " + name + " not found"));
        List<Product> productList = brand.getProducts();

        return productList.stream()
                .map(product -> modelMapper.map(product, ProductDisplayDto.class))
                .collect(Collectors.toList());
    }
}
