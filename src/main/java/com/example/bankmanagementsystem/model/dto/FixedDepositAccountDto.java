package com.example.bankmanagementsystem.model.dto;

import com.example.bankmanagementsystem.model.service.AccountNumberGenerator;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FixedDepositAccountDto {


    private Long accountNumber;

    private Long customer_id;

    private Long bank_id;


    private String tenure;


    public long generateAccountNumber() {
        this.accountNumber = Long.valueOf(AccountNumberGenerator.generateAccountNumber());
        return accountNumber;
    }
}
