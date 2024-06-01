package com.proyecto.gestock.product.infrastructure;

import com.proyecto.gestock.product.domain.Product;
import com.proyecto.gestock.product.dto.ProductDisplay;
import com.proyecto.gestock.product.dto.ProductDisplayDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //--------ADMIN--------//
    List<Product> findAllByNameContains(String namePart);
    List<Product> findAllByPriceGreaterThanEqualAndPriceLessThanEqual(BigDecimal min, BigDecimal max);
    List<Product> findAllByStockGreaterThanEqual(Integer stock);
    List<Product> findAllByStockLessThanEqual(Integer stock);
    List<Product> findAllByAvailable(Boolean available);
    List<Product> findAllByBrandId(Long id);
    List<Product> findAllByCategoryId(Long id);
    List<Product> findAllBySupplierId(Long id);

    //--------CUSTOMER--------//
    ProductDisplay findByNameAndAvailableTrueAndStockGreaterThan(String name, Integer stock);
    List<ProductDisplay> findByNameContainsAndAvailableTrueAndStockGreaterThan(String name, Integer stock);
    List<ProductDisplay> findAllByCategoryNameAndAvailableTrueAndStockGreaterThan(String categoryName, Integer stock);
}
