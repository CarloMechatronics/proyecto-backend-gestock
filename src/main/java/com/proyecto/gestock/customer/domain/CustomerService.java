package com.proyecto.gestock.customer.domain;

import com.proyecto.gestock.customer.dto.CustomerDTO;
import com.proyecto.gestock.customer.infrastructure.CustomerRepository;
import com.proyecto.gestock.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public CustomerDTO getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email);
        if(customer == null) {
            throw new ResourceNotFoundException("Customer not found");
        }
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public CustomerDTO getCustomerByUsername(String username) {
        Customer customer = customerRepository.findByUsername(username);
        if(customer == null) {
            throw new ResourceNotFoundException("Customer not found");
        }
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer = customerRepository.save(customer);
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        customerRepository.delete(customer);
    }
}
