package com.proyecto.gestock.useraccount.dto;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDTO {
    private String username;
    private String email;
    private String urlProfilePhoto;
    private String role;
}
