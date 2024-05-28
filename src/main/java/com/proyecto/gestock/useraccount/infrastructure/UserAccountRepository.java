package com.proyecto.gestock.useraccount.infrastructure;

import com.proyecto.gestock.useraccount.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}
