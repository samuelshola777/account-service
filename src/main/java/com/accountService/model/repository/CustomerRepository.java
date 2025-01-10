package com.accountService.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountService.model.Customer;

import java.util.UUID;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByBvn(String bvn);
    Optional<Customer> findByNin(String nin);
    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
