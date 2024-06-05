package com.proyecto.gestock.shoppingcart.domain;

import com.proyecto.gestock.customer.domain.Customer;
import com.proyecto.gestock.orderitem.domain.OrderItem;
import com.proyecto.gestock.purchaseorder.domain.PurchaseOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @DecimalMin(value = "0.0")
    @Column(nullable = false)
    private BigDecimal totalAmount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotNull
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(mappedBy = "shoppingCart")
    private PurchaseOrder purchaseOrder;

    public void calculateTotalAmount() {
        totalAmount = new BigDecimal("0.0");
        for (OrderItem orderItem : orderItems) {
            totalAmount = totalAmount.add(orderItem.getAmount());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart shoppingCart = (ShoppingCart) o;
        return Objects.equals(id, shoppingCart.id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", totalAmount=" + totalAmount +
                ", customer=" + (customer != null ? customer.getId() : "null") +
                ", orderItems=" + orderItems +
                '}';
    }
}
