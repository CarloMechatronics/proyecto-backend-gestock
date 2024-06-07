package com.proyecto.gestock.shoppingCart.domain;
import com.proyecto.gestock.customer.domain.Customer;
import com.proyecto.gestock.orderitem.domain.OrderItem;
import com.proyecto.gestock.purchaseorder.domain.PurchaseOrder;
import com.proyecto.gestock.shoppingcart.domain.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {
    private ShoppingCart shoppingCart;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("Customer1");
        customer.setEmail("customer1@example.com");

        shoppingCart = new ShoppingCart();
        shoppingCart.setTotalAmount(new BigDecimal("100.00"));
        shoppingCart.setCustomer(customer);

        OrderItem item1 = new OrderItem();
        item1.setId(1L);
        item1.setQuantity(1);

        OrderItem item2 = new OrderItem();
        item2.setId(2L);
        item2.setQuantity(1);

        shoppingCart.setOrderItems(Arrays.asList(item1, item2));
    }

    @Test
    public void testShoppingCartCreation() {
        assertNotNull(shoppingCart);
        assertEquals(new BigDecimal("100.00"), shoppingCart.getTotalAmount());
        assertEquals(customer, shoppingCart.getCustomer());
        assertEquals(2, shoppingCart.getOrderItems().size());
    }

    @Test
    public void testSetPurchaseOrder() {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(1L);

        shoppingCart.setPurchaseOrder(purchaseOrder);

        assertEquals(purchaseOrder, shoppingCart.getPurchaseOrder());
    }


}
