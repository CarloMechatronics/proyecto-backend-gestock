package com.proyecto.gestock.purchaseorder.domain;

import com.proyecto.gestock.shoppingcart.domain.ShoppingCart;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime orderDate;

    @NotNull
    @Column(nullable = false)
    private String status;

    @OneToOne
    private ShoppingCart shoppingCart;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseOrder purchaseOrder = (PurchaseOrder) o;
        return Objects.equals(id, purchaseOrder.id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                ", shoppingCart=" + (shoppingCart != null ? shoppingCart.getId() : "null") +
                '}';
    }
}
