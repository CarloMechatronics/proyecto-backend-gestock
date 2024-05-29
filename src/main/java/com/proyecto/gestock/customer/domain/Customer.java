package com.proyecto.gestock.customer.domain;

import com.proyecto.gestock.order.domain.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 30, max = 50)
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    private LocalDate registrationDate;

    @NotNull
    private String status;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();
}
