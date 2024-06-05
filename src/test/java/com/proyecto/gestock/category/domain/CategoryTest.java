package com.proyecto.gestock.category.domain;

import com.proyecto.gestock.product.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {
    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setName("Category1");
        category.setImageUrl("imageUrl1.png");

        Product product1 = new Product();
        product1.setName("Product1");
        product1.setDescription("BLA BLA BLA BLA Product1 description");
        product1.setStock(100);
        product1.setCategory(category);

        Product product2 = new Product();
        product2.setName("Product2");
        product2.setDescription("BLA BLA BLA BLA Product2 description");
        product2.setStock(200);
        product2.setCategory(category);

        Product product3 = new Product();
        product3.setName("Product3");
        product3.setDescription("BLA BLA BLA BLA Product3 description");
        product3.setStock(300);
        product3.setCategory(category);

        category.setProducts(Arrays.asList(product1, product2, product3));
    }

    @Test
    public void testCategoryCreation() {
        assertNotNull(category);
        assertEquals("Category1", category.getName());
        assertEquals("imageUrl1.png", category.getImageUrl());
    }

    @Test
    public void testAddProductToCategory() {
        Product product4 = new Product();
        product4.setName("Product4");
        product4.setDescription("BLA BLA BLA BLA Product4 description");
        product4.setStock(400);
        product4.setCategory(category);

        List<Product> products = new ArrayList<>(category.getProducts());
        products.add(product4);
        category.setProducts(products);

        assertEquals(4, category.getProducts().size());
        Product addedProduct = category.getProducts().get(3);
        assertEquals("Product4", addedProduct.getName());
        assertEquals("BLA BLA BLA BLA Product4 description", addedProduct.getDescription());
        assertEquals(400, addedProduct.getStock());
    }

    @Test
    public void testUpdateCategory() {
        String newName = "UpdatedCategory";
        String newImageUrl = "updatedImageUrl.png";

        category.setName(newName);
        category.setImageUrl(newImageUrl);

        assertEquals(newName, category.getName());
        assertEquals(newImageUrl, category.getImageUrl());
    }

    @Test
    public void testFindAllCategoryProducts() {
        List<Product> products = category.getProducts();

        assertEquals(3, products.size());
    }

    @Test
    public void testCategoryInitialization() {
        Category newCategory = new Category();
        assertNull(newCategory.getName());
        assertEquals("https://cdn4.iconfinder.com/data/icons/core-ui-outlined/32/outlined_placeholder-256.png", newCategory.getImageUrl());
        assertNotNull(newCategory.getProducts());
        assertTrue(newCategory.getProducts().isEmpty());
    }
}
