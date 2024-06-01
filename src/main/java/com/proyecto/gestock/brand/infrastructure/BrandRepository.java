package com.proyecto.gestock.brand.infrastructure;

import com.proyecto.gestock.brand.domain.Brand;
import com.proyecto.gestock.brand.dto.BrandDisplay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    //--------ADMIN--------//
    List<Brand> findAllByActive(Boolean active);
    //--------CUSTOMER--------//
    List<BrandDisplay> findAllByActiveTrue();
    List<BrandDisplay> findAllByNameContainsAndActiveTrue(String namePart);
}
