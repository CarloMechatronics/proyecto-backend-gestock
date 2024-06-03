package com.proyecto.gestock.product.infrastructure;

import com.proyecto.gestock.product.domain.Product;
import com.proyecto.gestock.product.dto.ProductDisplay;
import com.proyecto.gestock.product.dto.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //--------ADMIN--------//
    List<Product> findAllByNameContains(String namePart);
    List<Product> findAllByPriceGreaterThanEqualAndPriceLessThanEqual(BigDecimal min, BigDecimal max);
    List<Product> findAllByStockGreaterThanEqual(Integer stock);
    List<Product> findAllByStockLessThanEqual(Integer stock);

    //--------ANYONE--------//
    Optional<ProductInfo> findByNameAndStockGreaterThanEqual(String name, Integer stock);
    List<ProductDisplay> findAllByNameContainsAndStockGreaterThanEqual(String name, Integer stock);
    List<ProductDisplay> findAllByNameContainsAndPriceGreaterThanEqualAndPriceLessThanEqualAndStockGreaterThanEqual(String namePart, BigDecimal min, BigDecimal max, Integer stock);
}
