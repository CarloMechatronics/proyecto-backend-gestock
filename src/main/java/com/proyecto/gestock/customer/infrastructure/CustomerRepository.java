package com.proyecto.gestock.customer.infrastructure;

import com.proyecto.gestock.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);
    List<Customer> findAllByNameContains(String namePart);
    List<Customer> findAllByEmailContains(String emailPart);
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