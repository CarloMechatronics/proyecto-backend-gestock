package com.proyecto.gestock.brand.domain;

import com.proyecto.gestock.product.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BrandTest {
    private Brand brand;

    @BeforeEach
    public void setUp() {
        brand = new Brand();
        brand.setName("Brand 1");
        brand.setLogo("logo1.jpg");
        brand.setActive(true);

        Product product1 = new Product();
        product1.setName("Product1");
        product1.setDescription("BLA BLA BLA BLA Product1 description");
        product1.setStock(100);

        Product product2 = new Product();
        product2.setName("Product2");
        product2.setDescription("BLA BLA BLA BLA Product2 description");
        product2.setStock(200);

        Product product3 = new Product();
        product3.setName("Product3");
        product3.setDescription("BLA BLA BLA BLA Product3 description");
        product3.setStock(300);


        brand.setProducts(Arrays.asList(product1, product2, product3));
    }

    @Test
    public void testBrandCreation(){
        assertNotNull(brand);
        assertEquals("Brand 1", brand.getName());
        assertEquals("logo1.jpg", brand.getLogo());
    }

    @Test
    public void testAddProduct(){
        Product product4 = new Product();
        product4.setName("Product4");
        product4.setDescription("BLA BLA BLA BLA Product4 description");
        product4.setStock(100);
        List<Product> products = new ArrayList<>(brand.getProducts());
        products.add(product4);
        brand.setProducts(products);
        assertEquals(4, brand.getProducts().size());
    }

    @Test
    public void testRemoveProduct(){
        List<Product> products = new ArrayList<>(brand.getProducts());
        products.remove(0);
        brand.setProducts(products);
        assertEquals(2, brand.getProducts().size());
    }

    @Test
    public void testGetProducts(){
        List<Product> products = brand.getProducts();
        assertNotNull(products);
        assertEquals(3, products.size());

        Product product1 = products.get(0);
        assertEquals("Product1", product1.getName());
        assertEquals("BLA BLA BLA BLA Product1 description", product1.getDescription());
        assertEquals(100, product1.getStock());

        Product product2 = products.get(1);
        assertEquals("Product2", product2.getName());
        assertEquals("BLA BLA BLA BLA Product2 description", product2.getDescription());
        assertEquals(200, product2.getStock());

        Product product3 = products.get(2);
        assertEquals("Product3", product3.getName());
        assertEquals("BLA BLA BLA BLA Product3 description", product3.getDescription());
        assertEquals(300, product3.getStock());
    }


}
