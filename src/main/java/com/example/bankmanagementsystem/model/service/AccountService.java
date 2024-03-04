package com.example.bankmanagementsystem.model.service;

import com.example.bankmanagementsystem.model.dto.AccountDto;
import com.example.bankmanagementsystem.model.entity.Account;
import com.example.bankmanagementsystem.model.entity.Bank;
import com.example.bankmanagementsystem.model.entity.Customer;
import com.example.bankmanagementsystem.model.exception.AccountIdNotFoundException;
import com.example.bankmanagementsystem.model.exception.BankNotFoundException;
import com.example.bankmanagementsystem.model.mapper.AccountMapper;
import com.example.bankmanagementsystem.model.repository.AccountRepository;
import com.example.bankmanagementsystem.model.repository.BankRepository;
import com.example.bankmanagementsystem.model.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.bankmanagementsystem.model.mapper.AccountMapper.mapToAccountDto;


@Service

@Transactional
public class AccountService {

    private final CustomerRepository customerRepository;

    private final AccountRepository accountRepository;

    private final CustomerService customerService;

    private final BankRepository bankRepository;

    @Autowired
    public AccountService(CustomerRepository customerRepository, AccountRepository accountRepository, CustomerService customerService, BankRepository bankRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.bankRepository = bankRepository;
    }

    public AccountDto createAccount(AccountDto accountDto) {
        Account tempAccount = getAccountByAccountNumber(accountDto.getNumber());
        AccountDto accountDto1=null;
        if (tempAccount == null) {
            Customer customer = customerService.getCustomerById(accountDto.getCustomerId());
            Bank bank = bankRepository.findById(accountDto.getBankId()).orElseThrow(()-> new BankNotFoundException("Could not find bank"));
            Account account = AccountMapper.mapToAccount(accountDto, customer, bank);
            account.setBalance(0.0);
            account = accountRepository.save(account);

            accountDto1 = mapToAccountDto(account);
            return accountDto1;
        }
        return accountDto1;
    }


    public AccountDto getAccountById(Long Id) {
        Account account = accountRepository.findById(Id).orElseThrow(() -> new AccountIdNotFoundException("Account not found for ID:" + Id));
        AccountDto accountDto = mapToAccountDto(account);

        return accountDto;
    }

    public List<AccountDto> getAccount() {
        List<Account> accountList = accountRepository.findAll();
        List<AccountDto> accountDtoList = new ArrayList<>();
        for (Account account :accountList) {
           AccountDto accountDto = mapToAccountDto(account);
            accountDtoList.add(accountDto);
        }
        return accountDtoList;
    }

    public Account getAccountByAccountNumber(Long number){
        return accountRepository.findByNumber(number);


    }

    public void deleteAccountById(Long id){
        Account account= accountRepository.findById(id).orElseThrow(()-> new AccountIdNotFoundException("Account not found with ID:" +id));
                accountRepository.delete(account);

    }


}
