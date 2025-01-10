package com.accountService.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.accountService.model.Account;
import java.util.UUID;
import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByAccountNumber(String accountNumber);
    Optional<Account> findByCustomerId(UUID customerId);
}
