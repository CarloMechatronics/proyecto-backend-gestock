package com.proyecto.gestock.customer.domain;

import com.proyecto.gestock.purchaseorder.domain.PurchaseOrder;
import com.proyecto.gestock.shoppingcart.domain.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setRegistrationDate(LocalDate.now());

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomer(customer);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setCustomer(customer);

        customer.setShoppingCarts(List.of(shoppingCart));
        customer.setPurchaseOrders(List.of(purchaseOrder));
    }

    @Test
    public void testCustomerCreation() {
        assertNotNull(customer);
        assertEquals("John Doe", customer.getName());
        assertEquals("john.doe@example.com", customer.getEmail());
        assertEquals(LocalDate.now(), customer.getRegistrationDate());
    }

    @Test
    public void testAddShoppingCartToCustomer() {
        ShoppingCart newCart = new ShoppingCart();
        newCart.setCustomer(customer);

        List<ShoppingCart> shoppingCarts = new ArrayList<>(customer.getShoppingCarts());
        shoppingCarts.add(newCart);
        customer.setShoppingCarts(shoppingCarts);

        assertEquals(2, customer.getShoppingCarts().size());
        assertTrue(customer.getShoppingCarts().contains(newCart));
    }

    @Test
    public void testRemoveShoppingCartFromCustomer() {
        List<ShoppingCart> shoppingCarts = new ArrayList<>(customer.getShoppingCarts());
        ShoppingCart cartToRemove = shoppingCarts.get(0);
        shoppingCarts.remove(cartToRemove);
        customer.setShoppingCarts(shoppingCarts);

        assertEquals(0, customer.getShoppingCarts().size());
        assertFalse(customer.getShoppingCarts().contains(cartToRemove));
    }

    @Test
    public void testAddPurchaseOrderToCustomer() {
        PurchaseOrder newOrder = new PurchaseOrder();
        newOrder.setCustomer(customer);

        List<PurchaseOrder> purchaseOrders = new ArrayList<>(customer.getPurchaseOrders());
        purchaseOrders.add(newOrder);
        customer.setPurchaseOrders(purchaseOrders);

        assertEquals(2, customer.getPurchaseOrders().size());
        assertTrue(customer.getPurchaseOrders().contains(newOrder));
    }

    @Test
    public void testRemovePurchaseOrderFromCustomer() {
        List<PurchaseOrder> purchaseOrders = new ArrayList<>(customer.getPurchaseOrders());
        PurchaseOrder orderToRemove = purchaseOrders.get(0);
        purchaseOrders.remove(orderToRemove);
        customer.setPurchaseOrders(purchaseOrders);

        assertEquals(0, customer.getPurchaseOrders().size());
        assertFalse(customer.getPurchaseOrders().contains(orderToRemove));
    }

    @Test
    public void testUpdateCustomer() {
        String newName = "Jane Doe";
        String newEmail = "jane.doe@example.com";

        customer.setName(newName);
        customer.setEmail(newEmail);

        assertEquals(newName, customer.getName());
        assertEquals(newEmail, customer.getEmail());
    }


}