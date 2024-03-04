package com.example.bankmanagementsystem.model.repository;

import com.example.bankmanagementsystem.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account,Long> {


     Account findByNumber(Long number);






//    @Query("SELECT a FROM Account a WHERE a.number = :accountNumber")
//    Account findByAccountByAccountNumber(Long accountNumber);


    
}
