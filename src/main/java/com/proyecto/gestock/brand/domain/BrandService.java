package com.proyecto.gestock.brand.domain;

import com.proyecto.gestock.brand.dto.BrandDisplay;
import com.proyecto.gestock.brand.dto.BrandDisplayDto;
import com.proyecto.gestock.brand.infrastructure.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandService {
    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BrandService(BrandRepository brandRepository, ModelMapper modelMapper) {
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
    }

    //--------ADMIN--------//
    public List<Brand> findAllBrands() {
        return brandRepository.findAll();
    }

    //--------CUSTOMER--------//
    public List<BrandDisplayDto> findAllBrandsByActiveTrue() {
        List<BrandDisplay> brandDisplayList = brandRepository.findAllByActiveTrue();
        return brandDisplayList.stream()
                .map(brandDisplay -> modelMapper.map(brandDisplay, BrandDisplayDto.class))
                .collect(Collectors.toList());
    }

    public List<BrandDisplayDto> findAllByNameContains(String namePart) {
        List<BrandDisplay> brandDisplayList = brandRepository.findAllByNameContains(namePart);
        return brandDisplayList.stream()
                .map(brandDisplay -> modelMapper.map(brandDisplay, BrandDisplayDto.class))
                .collect(Collectors.toList());
    }
}
