package com.proyecto.gestock.customer.infrastructure;

import com.proyecto.gestock.AbstractContainerBaseTest;
import com.proyecto.gestock.customer.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest extends AbstractContainerBaseTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPassword("password123");
        customer.setRegistrationDate(LocalDate.now());

        entityManager.persist(customer);
        entityManager.flush();
    }

    @Test
    public void testCreateCustomer() {
        Customer newCustomer = new Customer();
        newCustomer.setName("Jane Doe");
        newCustomer.setEmail("jane.doe@example.com");
        newCustomer.setPassword("password456");
        newCustomer.setRegistrationDate(LocalDate.now());

        Customer savedCustomer = customerRepository.save(newCustomer);
        assertNotNull(savedCustomer);
        assertEquals(newCustomer.getName(), savedCustomer.getName());
        assertEquals(newCustomer.getEmail(), savedCustomer.getEmail());
    }

    @Test
    public void testFindById() {
        Customer foundCustomer = customerRepository.findById(customer.getId()).orElse(null);
        assertNotNull(foundCustomer);
        assertEquals(customer.getId(), foundCustomer.getId());
        assertEquals(customer.getName(), foundCustomer.getName());
        assertEquals(customer.getEmail(), foundCustomer.getEmail());
    }

    @Test
    public void testFindByEmail() {
        Customer foundCustomer = customerRepository.findByEmail(customer.getEmail()).orElse(null);
        assertNotNull(foundCustomer);
        assertEquals(customer.getEmail(), foundCustomer.getEmail());
    }

    @Test
    public void testFindByName() {
        Customer foundCustomer = customerRepository.findByName(customer.getName()).orElse(null);
        assertNotNull(foundCustomer);
        assertEquals(customer.getName(), foundCustomer.getName());
    }

    @Test
    public void testDeleteById() {
        customerRepository.deleteById(customer.getId());
        Customer foundCustomer = customerRepository.findById(customer.getId()).orElse(null);
        assertNull(foundCustomer);
    }

    @Test
    public void testUpdateCustomer() {
        customer.setName("Updated John Doe");
        Customer updatedCustomer = customerRepository.save(customer);
        assertNotNull(updatedCustomer);
        assertEquals("Updated John Doe", updatedCustomer.getName());
    }
}
