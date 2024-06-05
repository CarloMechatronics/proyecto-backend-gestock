package com.proyecto.gestock.authentication.domain;

import com.proyecto.gestock.authentication.dto.JwtAuthenticationResponseDTO;
import com.proyecto.gestock.authentication.dto.LogInDTO;
import com.proyecto.gestock.authentication.dto.SignInDTO;
import com.proyecto.gestock.configuration.JwtService;
import com.proyecto.gestock.customer.domain.Customer;
import com.proyecto.gestock.customer.infrastructure.CustomerRepository;
import com.proyecto.gestock.evento.SingingEvent.CustomerRegisteredEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final CustomerRepository customerRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public AuthenticationService(CustomerRepository customerRepository, JwtService jwtService, PasswordEncoder passwordEncoder, ApplicationEventPublisher eventPublisher) {
        this.customerRepository = customerRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
    }

    public JwtAuthenticationResponseDTO login(LogInDTO logInDTO) {
        Customer customer = customerRepository.findByEmail(logInDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        if (!passwordEncoder.matches(logInDTO.getPassword(), customer.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }

        JwtAuthenticationResponseDTO responseDTO = new JwtAuthenticationResponseDTO();
        responseDTO.setToken(jwtService.generateToken(customer));
        return responseDTO;
    }

    public JwtAuthenticationResponseDTO signIn(SignInDTO signInDTO) {
        if (customerRepository.findByEmail(signInDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        eventPublisher.publishEvent(new CustomerRegisteredEvent(signInDTO.getEmail(), signInDTO.getName()));
        Customer customer = new Customer();
        customer.setEmail(signInDTO.getEmail());
        customer.setPassword(passwordEncoder.encode(signInDTO.getPassword()));
        customer.setName(signInDTO.getName());
        customer.setRegistrationDate(signInDTO.getRegistrationDate());
        customerRepository.save(customer);
        JwtAuthenticationResponseDTO responseDTO = new JwtAuthenticationResponseDTO();
        responseDTO.setToken(jwtService.generateToken(customer));
        return responseDTO;
    }
}
