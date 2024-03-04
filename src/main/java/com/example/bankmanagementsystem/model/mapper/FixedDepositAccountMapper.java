package com.example.bankmanagementsystem.model.mapper;

import com.example.bankmanagementsystem.model.dto.AccountDto;
import com.example.bankmanagementsystem.model.dto.FixedDepositAccountDto;
import com.example.bankmanagementsystem.model.entity.*;

import java.time.LocalDate;

public class FixedDepositAccountMapper {
    public static FixedDepositAccount mapToFixedDepositAccount(FixedDepositAccountDto fixedDepositAccountDto, Customer customer, Bank bank) {
        FixedDepositAccount fixedDepositAccount=new FixedDepositAccount();

        fixedDepositAccount.setCreationTime(LocalDate.now());
        fixedDepositAccount.setCustomer(customer);
        fixedDepositAccount.setBank(bank);
        fixedDepositAccount.setAccountNumber(fixedDepositAccountDto.generateAccountNumber());
        fixedDepositAccount.setTenure(TenureType.valueOf(fixedDepositAccountDto.getTenure()));
        return fixedDepositAccount;

    }

    public static FixedDepositAccountDto mapToFixedAccountDto(FixedDepositAccount fixedDepositaccount){
        FixedDepositAccountDto fixedDepositAccountDto=new FixedDepositAccountDto();
        fixedDepositAccountDto.setCustomer_id(fixedDepositaccount.getCustomer().getId());
        fixedDepositAccountDto.generateAccountNumber();
        fixedDepositAccountDto.setBank_id(fixedDepositaccount.getBank().getId());
        fixedDepositAccountDto.setTenure(String.valueOf(fixedDepositaccount.getTenure()));


        fixedDepositAccountDto.setBank_id(fixedDepositaccount.getBank().getId());

        return fixedDepositAccountDto;
    }
}
