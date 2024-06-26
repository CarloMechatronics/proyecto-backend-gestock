package com.proyecto.gestock.customer.application;

import com.proyecto.gestock.customer.domain.Customer;
import com.proyecto.gestock.customer.domain.CustomerService;
import com.proyecto.gestock.customer.dto.CustomerCreateDto;
import com.proyecto.gestock.customer.dto.CustomerResponseDto;
import com.proyecto.gestock.customer.dto.CustomerUpdateDto;
import com.proyecto.gestock.product.domain.Product;
import com.proyecto.gestock.purchaseorder.domain.PurchaseOrder;
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

    //--------ADMIN--------//
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerService.findAllCustomers(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findCustomerById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/name/{name}")
    public ResponseEntity<Customer> getCustomerByName(@PathVariable String name) {
        return ResponseEntity.ok(customerService.findCustomerByName(name));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        return ResponseEntity.ok(customerService.findCustomerByEmail(email));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/search/name")
    public ResponseEntity<List<Customer>> getAllCustomersByNameContains(@RequestParam("name") String namePart) {
        return new ResponseEntity<>(customerService.findAllCustomersByNameContains(namePart), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/search/email")
    public ResponseEntity<List<Customer>> getAllCustomersByEmailContains(@RequestParam("email") String emailPart) {
        return new ResponseEntity<>(customerService.findAllCustomersByEmailContains(emailPart), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{customerId}/shopping-carts/{shoppingCartId}")
    public ResponseEntity<ShoppingCart> getCustomerShoppingCartById(@PathVariable Long customerId, @PathVariable Long shoppingCartId) {
        return new ResponseEntity<>(customerService.findCustomerShoppingCartByIds(customerId, shoppingCartId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{customerId}/purchase-orders/{purchaseOrderId}")
    public ResponseEntity<PurchaseOrder> getCustomerPurchaseOrderById(@PathVariable Long customerId, @PathVariable Long purchaseOrderId) {
        return new ResponseEntity<>(customerService.findCustomerPurchaseOrderByIds(customerId, purchaseOrderId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/shopping-carts")
    public ResponseEntity<List<ShoppingCart>> getAllCustomerShoppingCartsById(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findAllCustomerShoppingCartsById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/purchase-orders")
    public ResponseEntity<List<PurchaseOrder>> getAllCustomerPurchaseOrdersById(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findAllCustomerPurchaseOrdersById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerCreateDto customerCreateDto) {
        return new ResponseEntity<>(customerService.saveCustomer(customerCreateDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}/update")
    public ResponseEntity<Customer> editCustomerById(@PathVariable Long id, @RequestBody CustomerUpdateDto customerUpdateDto) {
        return new ResponseEntity<>(customerService.editCustomerById(id, customerUpdateDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //--------ANYONE--------//
    @PostMapping("/register")
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
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}