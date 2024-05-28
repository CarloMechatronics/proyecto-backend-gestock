package com.proyecto.gestock.useraccount.dto;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class UserAccountDTO {
    private String username;
    private String role;
}
