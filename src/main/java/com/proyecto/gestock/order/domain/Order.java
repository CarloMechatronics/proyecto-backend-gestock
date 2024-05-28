package com.proyecto.gestock.order.domain;

import com.proyecto.gestock.customer.domain.Customer;
import com.proyecto.gestock.orderitem.domain.OrderItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    LocalDateTime orderDate;

    @NotNull
    @Column(nullable = false)
    BigDecimal totalAmount;

    @NotNull
    @Column(nullable = false)
    String status;

//    @ManyToOne
//    Customer customer;

    @OneToMany
    @Column(nullable = false)
    @JoinColumn(nullable = false)
    List<OrderItem> orderItems;
}
