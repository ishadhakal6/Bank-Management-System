package com.example.bankmanagementsystem.model.repository;

import com.example.bankmanagementsystem.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
