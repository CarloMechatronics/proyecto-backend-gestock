package com.proyecto.gestock.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.proyecto.gestock.customer.domain.CustomerService;
import com.proyecto.gestock.useraccount.domain.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    private final CustomerService customerService;
    private final UserAccountService userAccountService;

    @Autowired
    public JwtService(@Lazy CustomerService customerService, @Lazy UserAccountService userAccountService) {
        this.customerService = customerService;
        this.userAccountService = userAccountService;
    }

    public String extractUsername(String token) {
        return JWT.decode(token).getSubject();
    }

    public String generateToken(UserDetails userDetails) {
        return createToken(userDetails.getUsername());
    }

    private String createToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * 60 * 60 * 5); // 5 horas

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(now)
                .withExpiresAt(expiration)
                .sign(algorithm);
    }

    public void validateToken(String token, String username) throws AuthenticationException {
        JWT.require(Algorithm.HMAC256(secret)).build().verify(token);

        UserDetails userDetails = loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserDetails loadUserByUsername(String username) {
        UserDetails user = null;
        try {
            user = customerService.loadUserByEmail(username);
        } catch (UsernameNotFoundException e) {
            user = userAccountService.loadUserByEmail(username);
        }

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return user;
    }
}
