package com.example.bankmanagementsystem.model.repository;

import com.example.bankmanagementsystem.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer findByName(String username);
}
