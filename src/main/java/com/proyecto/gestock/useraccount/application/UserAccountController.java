package com.proyecto.gestock.useraccount.application;

import com.proyecto.gestock.useraccount.domain.UserAccount;
import com.proyecto.gestock.useraccount.domain.UserAccountService;
import com.proyecto.gestock.useraccount.dto.UserAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserAccountController {
    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping
    public ResponseEntity<UserAccountDTO> createUserAccount(@RequestBody UserAccount userAccount) {
        UserAccountDTO createdUser = userAccountService.createUserAccount(userAccount);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAccountDTO> getUserAccountById(@PathVariable Long id) {
        UserAccountDTO userAccountDTO = userAccountService.getUserAccountById(id);
        return ResponseEntity.ok(userAccountDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserAccountDTO>> getAllUserAccounts() {
        List<UserAccountDTO> userAccounts = userAccountService.getAllUserAccounts();
        return ResponseEntity.ok(userAccounts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAccountDTO> updateUserAccount(@PathVariable Long id, @RequestBody UserAccount userAccount) {
        UserAccountDTO updatedUser = userAccountService.updateUserAccount(id, userAccount);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserAccount(@PathVariable Long id) {
        userAccountService.deleteUserAccount(id);
        return ResponseEntity.noContent().build();
    }
}
