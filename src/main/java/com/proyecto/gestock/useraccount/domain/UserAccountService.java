package com.proyecto.gestock.useraccount.domain;

import com.proyecto.gestock.authentication.dto.JwtAuthenticationResponseDTO;
import com.proyecto.gestock.authentication.dto.LogInDTO;
import com.proyecto.gestock.authentication.dto.SignInDTO;
import com.proyecto.gestock.configuration.JwtService;
import com.proyecto.gestock.exceptions.ResourceNotFoundException;
import com.proyecto.gestock.useraccount.dto.UserAccountDTO;
import com.proyecto.gestock.useraccount.infrastructure.UserAccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository, ModelMapper modelMapper, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserAccountDTO createUserAccount(UserAccount userAccount) {
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));  // Encode password
        UserAccount savedUserAccount = userAccountRepository.save(userAccount);
        return modelMapper.map(savedUserAccount, UserAccountDTO.class);
    }

    public UserAccountDTO getUserAccountById(Long id) {
        UserAccount user = userAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return modelMapper.map(user, UserAccountDTO.class);
    }

    public List<UserAccountDTO> getAllUserAccounts() {
        List<UserAccount> userAccounts = userAccountRepository.findAll();
        return userAccounts.stream()
                .map(userAccount -> modelMapper.map(userAccount, UserAccountDTO.class))
                .collect(Collectors.toList());
    }

    public UserAccountDTO updateUserAccount(Long id, UserAccount userAccount) {
        UserAccount existingUser = userAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        existingUser.setUsername(userAccount.getUsername());
        existingUser.setEmail(userAccount.getEmail());
        if (!userAccount.getPassword().equals(existingUser.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        }
        UserAccount updatedUser = userAccountRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserAccountDTO.class);
    }

    public void deleteUserAccount(Long id) {
        UserAccount user = userAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userAccountRepository.delete(user);
    }

    public UserAccount findByEmail(String email) {
        return userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserDetails loadUserByEmail(String email) {
        UserAccount userAccount = findByEmail(email);
        return new User(userAccount.getEmail(), userAccount.getPassword(), List.of(new SimpleGrantedAuthority(userAccount.getRole())));
    }
}