package com.proyecto.gestock.customer.application;

import com.proyecto.gestock.customer.domain.Customer;
import com.proyecto.gestock.customer.domain.CustomerService;
import com.proyecto.gestock.customer.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /*
    * C R U D
    * crear una lista de productos para comprar
    * obtener productos disponibles
    * obtener lista creada
    * actualizar lista de productos a comprar --update
    * obtener precio de productos
    * eliminar producto de la lista -- Update
    * eliminar lista de productos
    *
    */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping("/email")
    public ResponseEntity<CustomerDTO> getCustomerByEmail(@RequestParam String email) {
        return ResponseEntity.ok(customerService.getCustomerByEmail(email));
    }

//    @GetMapping("/username")
//    public ResponseEntity<CustomerDTO> getCustomerByUsername(@RequestParam String username) {
//        return ResponseEntity.ok(customerService.getCustomerByUsername(username));
//    }

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

}
