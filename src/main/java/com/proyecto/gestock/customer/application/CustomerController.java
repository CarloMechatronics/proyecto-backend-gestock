package com.proyecto.gestock.customer.application;

import com.proyecto.gestock.customer.domain.Customer;
import com.proyecto.gestock.customer.domain.CustomerService;
import com.proyecto.gestock.customer.dto.CustomerCreateDto;
import com.proyecto.gestock.customer.dto.CustomerResponseDto;
import com.proyecto.gestock.customer.dto.CustomerUpdateDto;
import com.proyecto.gestock.shoppingcart.domain.ShoppingCart;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.Bidi;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerService.findAllCustomers(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findCustomerById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all/name/{name}/")
    public ResponseEntity<Customer> getCustomerByName(@PathVariable String name) {
        return ResponseEntity.ok(customerService.findCustomerByName(name));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        return ResponseEntity.ok(customerService.findCustomerByEmail(email));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/all")
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerCreateDto customerCreateDto) {
        return new ResponseEntity<>(customerService.saveCustomer(customerCreateDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //--------ANYONE--------//
    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<CustomerResponseDto> registerCustomer(@RequestBody CustomerCreateDto customerCreateDto) {
        return new ResponseEntity<>(customerService.registerCustomer(customerCreateDto), HttpStatus.CREATED);
    }



    //--------CUSTOMER--------//
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/shopping-cart/{id}")
    public ResponseEntity<ShoppingCart> getShoppingCart(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findShoppingCartById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/shopping-cart/{id}/totalPrice")
    public ResponseEntity<BigDecimal> getTotalPrice(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findTotalAmountById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/{customerId}/orders")
    public ResponseEntity<ShoppingCart> createShoppingCart(@PathVariable Long customerId, @RequestBody List<Long> productIds) {
        return new ResponseEntity<>(customerService.createShoppingCartByIds(customerId, productIds), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable Long id, @RequestBody CustomerUpdateDto customerUpdateDto) {
        return new ResponseEntity<>(customerService.updateCustomerById(id, customerUpdateDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/orders/{id}")
    public ResponseEntity<ShoppingCart> updateShoppingCartByIds(@PathVariable Long id, @RequestBody List<Long> productIds) {
        return new ResponseEntity<>(customerService.updateShoppingCartByIds(id, productIds), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/orders/{orderId}/products/{productId}")
    public ResponseEntity<ShoppingCart> updateShoppingCartItemQuantity(@PathVariable Long orderId, @PathVariable Long productId, @RequestParam Integer quantity) {
        return new ResponseEntity<>(customerService.updateShoppingCartItemQuantityByIds(orderId, productId, quantity), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteShoppingCartById(@PathVariable Long id) {
        customerService.deleteShoppingCartById(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
