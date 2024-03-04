package com.example.bankmanagementsystem.model.mapper;

import com.example.bankmanagementsystem.model.dto.AccountDto;
import com.example.bankmanagementsystem.model.entity.Account;
import com.example.bankmanagementsystem.model.entity.AccountType;
import com.example.bankmanagementsystem.model.entity.Bank;
import com.example.bankmanagementsystem.model.entity.Customer;

import java.time.LocalDate;

public class AccountMapper {

    public static Account mapToAccount(AccountDto accountDto, Customer customer, Bank bank) {
        Account account=new Account();
        account.setNumber(accountDto.generateAccountNumber());
        account.setType(AccountType.valueOf(accountDto.getType()));

        account.setDate(LocalDate.now());

        account.setCustomer(customer);
        account.setBank(bank);
        return account;

    }

    public static AccountDto mapToAccountDto(Account account){
        AccountDto accountDto=new AccountDto();
        accountDto.setCustomerId(account.getCustomer().getId());
        accountDto.generateAccountNumber();
        accountDto.setDate(account.getDate());
        accountDto.setType(String.valueOf(account.getType()));

        accountDto.setBankId(account.getBank().getId());

        return accountDto;
    }
}
