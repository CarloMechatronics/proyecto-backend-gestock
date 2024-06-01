package com.proyecto.gestock.product.infrastructure;

import com.proyecto.gestock.product.domain.Product;
import com.proyecto.gestock.product.dto.ProductDisplay;
import com.proyecto.gestock.product.dto.ProductDisplayDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //--------ADMIN--------//
    List<Product> findAllByCategoryId(Long id);
    List<Product> findAllByAvailable(Boolean available);
    List<Product> findAllByStockGreaterThanEqual(Integer stock);
    List<Product> findAllByStockLessThanEqual(Integer stock);

    //--------CUSTOMER--------//
    List<ProductDisplay> findByNameContainsAndAvailableTrueAndStockGreaterThan(String name, Integer stock);
    ProductDisplay findByNameAndAvailableTrueAndStockGreaterThan(String name, Integer stock);
    List<ProductDisplay> findAllByAvailableTrueAndCategoryNameAndStockGreaterThan(String categoryName, Integer stock);
}
