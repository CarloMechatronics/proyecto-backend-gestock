package com.proyecto.gestock.shoppingcart.infrastructure;

import com.proyecto.gestock.shoppingcart.domain.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
