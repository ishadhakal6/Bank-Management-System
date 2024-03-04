package com.example.bankmanagementsystem.model.mapper;

import com.example.bankmanagementsystem.model.dto.BankDto;
import com.example.bankmanagementsystem.model.entity.Bank;

public class BankMapper {

    public static Bank mapToBank(BankDto bankDto){
        Bank bank=new Bank();
        bank.setName(bankDto.getName());
        bank.setAddress(bankDto.getAddress());
        return bank;
    }

    public static BankDto mapToBankDto(Bank bank){
        BankDto bankDto=new BankDto();
        bankDto.setName(bank.getName());
        bankDto.setAddress(bank.getAddress());
        return bankDto;
    }
}
