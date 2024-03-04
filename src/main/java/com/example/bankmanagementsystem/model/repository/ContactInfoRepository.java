package com.example.bankmanagementsystem.model.repository;

import com.example.bankmanagementsystem.model.entity.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactInfoRepository extends JpaRepository<ContactInfo,Long> {
}
