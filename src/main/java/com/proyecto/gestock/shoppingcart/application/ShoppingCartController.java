package com.proyecto.gestock.shoppingcart.application;

import com.proyecto.gestock.shoppingcart.domain.ShoppingCart;
import com.proyecto.gestock.shoppingcart.domain.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShoppingCart>> getAllShoppingCarts() {
        return new ResponseEntity<>(shoppingCartService.findAllShoppingCarts(), HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<ShoppingCart> getShoppingCartById(@PathVariable Long id) {
        return new ResponseEntity<>(shoppingCartService.findShoppingCartById(id), HttpStatus.OK);
    }


    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteShoppingCartById(@PathVariable Long id) {
        shoppingCartService.deleteShoppingCartById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
