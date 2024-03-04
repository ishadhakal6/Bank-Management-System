package com.example.bankmanagementsystem.model.repository;

import com.example.bankmanagementsystem.model.entity.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BankRepository extends JpaRepository<Bank,Long> {

    Page<Bank> findAll(Pageable pageable);
    Bank findByName(String name);
}
