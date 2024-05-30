package com.proyecto.gestock.useraccount.domain;

import com.proyecto.gestock.exceptions.ResourceNotFoundException;
import com.proyecto.gestock.useraccount.dto.UserAccountDTO;
import com.proyecto.gestock.useraccount.infrastructure.UserAccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserAccountService(UserAccountRepository userAccountRepository, ModelMapper modelMapper) {
        this.userAccountRepository = userAccountRepository;
        this.modelMapper = modelMapper;
    }


    public UserAccountDTO createUserAccount(UserAccount userAccount) {
        UserAccount savedUserAccount = userAccountRepository.save(userAccount);
        return modelMapper.map(savedUserAccount, UserAccountDTO.class);
    }


    public UserAccountDTO getUserAccountById(Long id) {
        UserAccount user = userAccountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return modelMapper.map(user, UserAccountDTO.class);
    }

    public List<UserAccountDTO> getAllUserAccounts() {
        List<UserAccount> userAccounts = userAccountRepository.findAll();
        if(userAccounts.isEmpty()) {
            return new ArrayList<>();
        }
        List<UserAccountDTO> user = userAccounts.stream()
                .map(userAccount -> modelMapper.map(userAccount, UserAccountDTO.class))
                .collect(Collectors.toList());
        return user;
    }

    public UserAccountDTO updateUserAccount(Long id, UserAccount userAccount) {
        UserAccount existingUser = userAccountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        existingUser.setUsername(userAccount.getUsername());
        existingUser.setEmail(userAccount.getEmail());
        existingUser.setPassword(userAccount.getPassword());
        UserAccount updatedUser = modelMapper.map(existingUser, UserAccount.class);
        return modelMapper.map(updatedUser, UserAccountDTO.class);
    }

    public void deleteUserAccount(Long id) {
        UserAccount user = userAccountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userAccountRepository.delete(user);
    }
}
