package com.proyecto.gestock.customer.application;

import com.proyecto.gestock.customer.domain.Customer;
import com.proyecto.gestock.customer.domain.CustomerService;
import com.proyecto.gestock.customer.dto.CustomerDTO;
import com.proyecto.gestock.product.domain.Product;
import com.proyecto.gestock.purchaseorder.domain.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping("/email")
    public ResponseEntity<CustomerDTO> getCustomerByEmail(@RequestParam String email) {
        return ResponseEntity.ok(customerService.getCustomerByEmail(email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @PostMapping("/{customerId}/orders")
    public ResponseEntity<PurchaseOrder> createPurchaseOrder(@PathVariable Long customerId,
                                                             @RequestBody List<Long> productIds) {
        PurchaseOrder purchaseOrder = customerService.createPurchaseOrder(customerId, productIds);
        return new ResponseEntity<>(purchaseOrder, HttpStatus.CREATED);
    }

    @GetMapping("/products/available")
    public ResponseEntity<List<Product>> getAvailableProducts() {
        List<Product> availableProducts = customerService.getAvailableProducts();
        return ResponseEntity.ok(availableProducts);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrder(@PathVariable Long id) {
        PurchaseOrder purchaseOrder = customerService.getPurchaseOrder(id);
        return ResponseEntity.ok(purchaseOrder);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<PurchaseOrder> updatePurchaseOrder(@PathVariable Long id, @RequestBody List<Long> productIds) {
        PurchaseOrder updatedPurchaseOrder = customerService.updatePurchaseOrder(id, productIds);
        return ResponseEntity.ok(updatedPurchaseOrder);
    }

    @GetMapping("/orders/{id}/totalPrice")
    public ResponseEntity<BigDecimal> getTotalPrice(@PathVariable Long id) {
        BigDecimal totalPrice = customerService.getTotalPrice(id);
        return ResponseEntity.ok(totalPrice);
    }

    @PutMapping("/orders/{orderId}/products/{productId}")
    public ResponseEntity<PurchaseOrder> updateOrderItemQuantity(@PathVariable Long orderId, @PathVariable Long productId, @RequestParam Integer quantity) {
        PurchaseOrder updatedPurchaseOrder = customerService.updateOrderItemQuantity(orderId, productId, quantity);
        return ResponseEntity.ok(updatedPurchaseOrder);
    }


    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deletePurchaseOrder(@PathVariable Long id) {
        customerService.deletePurchaseOrder(id);
        return ResponseEntity.noContent().build();
    }
}
