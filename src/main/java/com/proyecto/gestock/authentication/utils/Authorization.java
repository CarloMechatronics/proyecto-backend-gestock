package com.proyecto.gestock.authentication.utils;

import com.proyecto.gestock.customer.domain.Customer;
import com.proyecto.gestock.customer.domain.CustomerService;
import com.proyecto.gestock.useraccount.domain.UserAccount;
import com.proyecto.gestock.useraccount.domain.UserAccountService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class Authorization {
    private final CustomerService customerService;
    private final UserAccountService userAccountService;

    @Autowired
    public Authorization(@Lazy CustomerService customerService,@Lazy UserAccountService userAccountService) {
        this.customerService = customerService;
        this.userAccountService = userAccountService;
    }

    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            UserAccount userAccount = userAccountService.findByEmail(username);
            return userAccount != null && userAccount.getRole().equals("ADMIN");
        }
        return false;
    }

//    public boolean isCustomer() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication.getPrincipal() instanceof UserDetails) {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            String username = userDetails.getUsername();
//            Customer customer = customerService.findCustomerByEmail(username);
//            return customer != null;
//        }
//        return false;
//    }
//
//    public String getCurrentUserEmail(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        try {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            return userDetails.getUsername();
//        } catch (ClassCastException e) {
//            return null;
//        }
//    }

}
