package com.proyecto.gestock.orderitem.domain;
<<<<<<< HEAD
import com.proyecto.gestock.product.domain.Product;
import com.proyecto.gestock.shoppingcart.domain.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class OrderItemTest {
    private OrderItem orderItem;
    private ShoppingCart shoppingCart;
    private Product product;

    @BeforeEach
    public void setUp() {
        shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        orderItem = new OrderItem();
        orderItem.setQuantity(2);
        orderItem.setPrice(new BigDecimal("19.99"));
        orderItem.setShoppingCart(shoppingCart);
        orderItem.setProduct(product);
    }

    @Test
    public void testOrderItemCreation() {
        assertNotNull(orderItem);
        assertEquals(2, orderItem.getQuantity());
        assertEquals(new BigDecimal("19.99"), orderItem.getPrice());
        assertEquals(shoppingCart, orderItem.getShoppingCart());
        assertEquals(product, orderItem.getProduct());
    }

    @Test
    public void testSetQuantity() {
        orderItem.setQuantity(5);
        assertEquals(5, orderItem.getQuantity());
    }

    @Test
    public void testSetPrice() {
        BigDecimal newPrice = new BigDecimal("29.99");
        orderItem.setPrice(newPrice);
        assertEquals(newPrice, orderItem.getPrice());
    }

    @Test
    public void testSetShoppingCart() {
        ShoppingCart newCart = new ShoppingCart();
        newCart.setId(2L);
        orderItem.setShoppingCart(newCart);
        assertEquals(newCart, orderItem.getShoppingCart());
    }

    @Test
    public void testSetProduct() {
        Product newProduct = new Product();
        newProduct.setId(2L);
        newProduct.setName("New Test Product");
        orderItem.setProduct(newProduct);
        assertEquals(newProduct, orderItem.getProduct());
    }

    @Test
    public void testOrderItemEquality() {
        OrderItem sameOrderItem = new OrderItem();
        sameOrderItem.setId(orderItem.getId());
        sameOrderItem.setQuantity(orderItem.getQuantity());
        sameOrderItem.setPrice(orderItem.getPrice());
        sameOrderItem.setShoppingCart(orderItem.getShoppingCart());
        sameOrderItem.setProduct(orderItem.getProduct());

        assertEquals(orderItem, sameOrderItem);
        assertEquals(orderItem.hashCode(), sameOrderItem.hashCode());
    }

    @Test
    public void testOrderItemInequality() {
        OrderItem differentOrderItem = new OrderItem();
        differentOrderItem.setId(2L);
        differentOrderItem.setQuantity(3);
        differentOrderItem.setPrice(new BigDecimal("29.99"));
        differentOrderItem.setShoppingCart(new ShoppingCart());
        differentOrderItem.setProduct(new Product());

        assertNotEquals(orderItem, differentOrderItem);
        assertNotEquals(orderItem.hashCode(), differentOrderItem.hashCode());
    }

    @Test
    public void testOrderItemToString() {
        String expectedString = "OrderItem{" +
                "id=" + orderItem.getId() +
                ", quantity=" + orderItem.getQuantity() +
                ", price=" + orderItem.getPrice() +
                ", order=" + (orderItem.getShoppingCart() != null ? orderItem.getShoppingCart().getId() : "null") +
                ", product=" + (orderItem.getProduct() != null ? orderItem.getProduct().getId() : "null") +
                '}';
        assertEquals(expectedString, orderItem.toString());
    }
=======

public class OrderItemTest {
>>>>>>> d9032df2b5372ae22aca1cfdc394eb473e8c5630
}
