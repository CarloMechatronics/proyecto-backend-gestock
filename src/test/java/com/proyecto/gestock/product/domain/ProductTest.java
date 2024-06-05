package com.proyecto.gestock.product.domain;
import com.proyecto.gestock.brand.domain.Brand;
import com.proyecto.gestock.category.domain.Category;
import com.proyecto.gestock.orderitem.domain.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private Product product;
    private Brand brand;
    private Category category;

    @BeforeEach
    public void setUp() {
        brand = new Brand();
        brand.setName("BrandName");

        category = new Category();
        category.setName("CategoryName");

        product = new Product();
        product.setName("ProductName");
        product.setLogo("ProductLogo.png");
        product.setDescription("Product Description");
        product.setPrice(BigDecimal.valueOf(19.99));
        product.setStock(100);
        product.setBrand(brand);
        product.setCategory(category);
        product.setOrderItems(new ArrayList<>());
    }

    @Test
    public void testProductCreation() {
        assertNotNull(product);
        assertEquals("ProductName", product.getName());
        assertEquals("ProductLogo.png", product.getLogo());
        assertEquals("Product Description", product.getDescription());
        assertEquals(BigDecimal.valueOf(19.99), product.getPrice());
        assertEquals(100, product.getStock());
        assertEquals(brand, product.getBrand());
        assertEquals(category, product.getCategory());
    }

    @Test
    public void testAddStock() {
        product.addStock(50);
        assertEquals(150, product.getStock());
    }

    @Test
    public void testSubtractStock() {
        product.subtractStock(50);
        assertEquals(50, product.getStock());
    }

    @Test
    public void testAddStockNegativeAmount() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            product.addStock(-10);
        });
        assertEquals("Amount must be positive", exception.getMessage());
    }

    @Test
    public void testSubtractStockNegativeAmount() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            product.subtractStock(-10);
        });
        assertEquals("Amount must be positive", exception.getMessage());
    }

    @Test
    public void testSubtractStockInsufficientAmount() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            product.subtractStock(200);
        });
        assertEquals("Insufficient stock to subtract", exception.getMessage());
    }

    @Test
    public void testEqualsAndHashCode() {
        Product anotherProduct = new Product();
        anotherProduct.setId(product.getId());

        assertEquals(product, anotherProduct);
        assertEquals(product.hashCode(), anotherProduct.hashCode());
    }

    @Test
    public void testToString() {
        String expectedString = "Product{" +
                "id=" + product.getId() +
                ", name='" + product.getName() + '\'' +
                ", description='" + product.getDescription() + '\'' +
                ", price=" + product.getPrice() +
                ", stock=" + product.getStock() +
                ", brand=" + (brand != null ? brand.getId() : "null") +
                ", category=" + (category != null ? category.getId() : "null") +
                '}';
        assertEquals(expectedString, product.toString());
    }
}
