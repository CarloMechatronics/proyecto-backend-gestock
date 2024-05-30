package com.proyecto.gestock.customer.infrastructure;

import com.proyecto.gestock.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
//    Customer findByUsername(String username);
}

/*
 * C R U D
 * crear una lista de productos para comprar
 * obtener productos disponibles
 * obtener lista creada
 * actualizar lista de productos a comprar
 * obtener precio de productos
 * eliminar producto de la lista -- Update
 * eliminar lista de productos
 */