package com.example.bankmanagementsystem.model.repository;

import com.example.bankmanagementsystem.model.entity.FixedDepositAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixedDepositAccountRepository extends JpaRepository<FixedDepositAccount,Long> {
    FixedDepositAccount findByAccountNumber(Long accountNumber);
}
