package com.proyecto.gestock.shoppingcart.domain;

import com.proyecto.gestock.authentication.utils.Authorization;
import com.proyecto.gestock.exceptions.ResourceNotFoundException;
import com.proyecto.gestock.exceptions.UnauthorizedOperationException;
import com.proyecto.gestock.shoppingcart.infrastructure.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final Authorization authorization;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, Authorization authorization) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.authorization = authorization;
    }

    //--------ADMIN--------//
    //----GET----//
    public List<ShoppingCart> findAllShoppingCarts() {
        return shoppingCartRepository.findAll();
    }

    public ShoppingCart findShoppingCartById(Long id) {
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
        return shoppingCartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping Cart with id "+ id + " not found"));
    }

    //----DELETE----//
    public void deleteShoppingCartById(Long id) {
        if(!authorization.isAdmin()) {
            throw new UnauthorizedOperationException("You are not authorized to view this product");
        }
        if (!shoppingCartRepository.existsById(id))
            throw new ResourceNotFoundException("Shopping Cart with id "+ id + " not found");

        shoppingCartRepository.deleteById(id);
    }
}
