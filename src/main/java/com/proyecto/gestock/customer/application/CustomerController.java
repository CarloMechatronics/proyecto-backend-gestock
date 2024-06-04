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
    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerService.findAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findCustomerById(id));
    }

    @GetMapping("/all/name/{name}/")
    public ResponseEntity<Customer> getCustomerByName(@PathVariable String name) {
        return ResponseEntity.ok(customerService.findCustomerByName(name));
    }

    @GetMapping("/all/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        return ResponseEntity.ok(customerService.findCustomerByEmail(email));
    }

    @GetMapping("/all/name")
    public ResponseEntity<List<Customer>> getAllCustomersByNameContains(@RequestParam("name") String namePart) {
        return new ResponseEntity<>(customerService.findAllCustomersByNameContains(namePart), HttpStatus.OK);
    }

    @GetMapping("/all/email")
    public ResponseEntity<List<Customer>> getAllCustomersByEmailContains(@RequestParam("email") String emailPart) {
        return new ResponseEntity<>(customerService.findAllCustomersByEmailContains(emailPart), HttpStatus.OK);
    }

    @GetMapping("/all/{id}/shopping-carts")
    public ResponseEntity<List<ShoppingCart>> getAllCustomerShoppingCartsById(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findAllCustomerShoppingCartsById(id), HttpStatus.OK);
    }

    @GetMapping("/all/{id}/purchase-orders")
    public ResponseEntity<List<PurchaseOrder>> getAllCustomerPurchaseOrdersById(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findAllCustomerPurchaseOrdersById(id), HttpStatus.OK);
    }

    @PostMapping("/all")
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerCreateDto customerCreateDto) {
        return new ResponseEntity<>(customerService.saveCustomer(customerCreateDto), HttpStatus.CREATED);
    }

    @PatchMapping("/all/{id}/update")
    public ResponseEntity<Customer> editCustomerById(@PathVariable Long id, @RequestBody CustomerUpdateDto customerUpdateDto) {
        return new ResponseEntity<>(customerService.editCustomerById(id, customerUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //--------ANYONE--------//
    @PostMapping
    public ResponseEntity<CustomerResponseDto> registerCustomer(@RequestBody CustomerCreateDto customerCreateDto) {
        return new ResponseEntity<>(customerService.registerCustomer(customerCreateDto), HttpStatus.CREATED);
    }



    //--------CUSTOMER--------//
    @GetMapping("/shopping-cart/{id}")
    public ResponseEntity<ShoppingCart> getShoppingCart(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findShoppingCartById(id), HttpStatus.OK);
    }

    @GetMapping("/shopping-cart/{id}/totalPrice")
    public ResponseEntity<BigDecimal> getTotalPrice(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findTotalAmountById(id), HttpStatus.OK);
    }

    @PostMapping("/{customerId}/orders")
    public ResponseEntity<ShoppingCart> createShoppingCart(@PathVariable Long customerId, @RequestBody List<Long> productIds) {
        return new ResponseEntity<>(customerService.createShoppingCartByIds(customerId, productIds), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable Long id, @RequestBody CustomerUpdateDto customerUpdateDto) {
        return new ResponseEntity<>(customerService.updateCustomerById(id, customerUpdateDto), HttpStatus.OK);
    }

    @PatchMapping("/orders/{id}")
    public ResponseEntity<ShoppingCart> updateShoppingCartByIds(@PathVariable Long id, @RequestBody List<Long> productIds) {
        return new ResponseEntity<>(customerService.updateShoppingCartByIds(id, productIds), HttpStatus.OK);
    }

    @PatchMapping("/orders/{orderId}/products/{productId}")
    public ResponseEntity<ShoppingCart> updateShoppingCartItemQuantity(@PathVariable Long orderId, @PathVariable Long productId, @RequestParam Integer quantity) {
        return new ResponseEntity<>(customerService.updateShoppingCartItemQuantityByIds(orderId, productId, quantity), HttpStatus.OK);
    }


    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteShoppingCartById(@PathVariable Long id) {
        customerService.deleteShoppingCartById(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
