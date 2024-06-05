package com.proyecto.gestock.purchaseorder.domain;

import com.proyecto.gestock.customer.domain.Customer;
import com.proyecto.gestock.shoppingcart.domain.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PurchaseOrderTest {
    private PurchaseOrder purchaseOrder;

    @BeforeEach
    public void setUp() {
        Customer customer = new Customer();
        customer.setName("Customer1");
        customer.setEmail("Customer1@example.com");
        customer.setRegistrationDate(LocalDateTime.now().minusDays(1).toLocalDate());

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setTotalAmount(new BigDecimal("100.00"));
        shoppingCart.setCustomer(customer);

        purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderDate(LocalDateTime.now());
        purchaseOrder.setStatus("Pending");
        purchaseOrder.setShoppingCart(shoppingCart);
        purchaseOrder.setCustomer(customer);
    }

    @Test
    public void testPurchaseOrderCreation() {
        assertNotNull(purchaseOrder);
        assertNotNull(purchaseOrder.getOrderDate());
        assertEquals("Pending", purchaseOrder.getStatus());
        assertNotNull(purchaseOrder.getShoppingCart());
        assertNotNull(purchaseOrder.getCustomer());
    }

    @Test
    public void testPurchaseOrderUpdate() {
        purchaseOrder.setStatus("Confirmed");
        assertEquals("Confirmed", purchaseOrder.getStatus());
    }
}
