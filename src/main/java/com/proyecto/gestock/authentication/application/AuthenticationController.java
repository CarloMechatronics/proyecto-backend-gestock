package com.proyecto.gestock.authentication.application;

import com.proyecto.gestock.authentication.domain.AuthenticationService;
import com.proyecto.gestock.authentication.dto.JwtAuthenticationResponseDTO;
import com.proyecto.gestock.authentication.dto.LogInDTO;
import com.proyecto.gestock.authentication.dto.SignInDTO;
import com.proyecto.gestock.authentication.utils.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private AuthenticationService authenticationService;


     @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
         this.authenticationService = authenticationService;
     }

     @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponseDTO> login(@RequestBody LogInDTO logInDTO){
         return ResponseEntity.ok(authenticationService.login(logInDTO));
     }

     @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponseDTO> signin(@RequestBody SignInDTO signInDTO){
            return ResponseEntity.ok(authenticationService.signIn(signInDTO));
     }

}
