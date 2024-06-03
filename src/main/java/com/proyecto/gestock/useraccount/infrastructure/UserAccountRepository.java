package com.proyecto.gestock.useraccount.infrastructure;

import com.proyecto.gestock.useraccount.domain.UserAccount;
import com.proyecto.gestock.useraccount.dto.UserAccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> { ;
}

    /*
    * C R U D
    * crear una lista de productos para comprar
    * obtener productos disponibles
    * obtener lista creada
    * actualizar lista de productos a comprar
    * obtener precio de productos
    * eliminar producto de la lista -- Update
    * eliminar lista de productos
    */