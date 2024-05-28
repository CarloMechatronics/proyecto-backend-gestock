package com.proyecto.gestock.customer.infrastructure;

import com.proyecto.gestock.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
    Customer findByUsername(String username);
}
