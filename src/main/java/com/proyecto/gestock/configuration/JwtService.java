package com.proyecto.gestock.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.proyecto.gestock.customer.domain.Customer;
import com.proyecto.gestock.customer.domain.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    private final CustomerService customerService;

    @Autowired
    public JwtService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public String getCustomerName(String token) {
        return JWT.decode(token).getSubject();
    }

    public String generateToken(Customer customer) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * 60 * 60 * 5);

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withSubject(customer.getEmail())
                .withClaim("name", customer.getName())
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .sign(algorithm);
    }
    public void validateToken(String token, String email) throws AuthenticationException {
            JWT.require(Algorithm.HMAC256(secret)).build().verify(token);

            UserDetails userDetails = customerService.userDetailsService().loadUserByUsername(email);

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    token,
                    userDetails.getAuthorities());
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
    }
}
