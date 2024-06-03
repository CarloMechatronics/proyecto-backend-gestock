package com.proyecto.gestock.brand.infrastructure;

import com.proyecto.gestock.brand.domain.Brand;
import com.proyecto.gestock.brand.dto.BrandDisplay;
import com.proyecto.gestock.product.dto.ProductDisplayDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    //--------ADMIN--------//
    List<Brand> findAllByActive(Boolean active);
    //--------CUSTOMER--------//
    List<BrandDisplay> findAllByActiveTrue();
    List<BrandDisplay> findAllByNameContainsAndActiveTrue(String namePart);
    // When user clicks a brand name or logo and then retrieve all products in a service method
    Optional<Brand> findByNameAndActiveTrue(String name);
}
