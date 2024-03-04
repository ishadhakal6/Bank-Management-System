package com.example.bankmanagementsystem.model.controller;

import com.example.bankmanagementsystem.model.dto.AccountDto;

import com.example.bankmanagementsystem.model.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank/account")
public class AccountController {

    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody @Valid AccountDto accountDto){
        AccountDto createdAccountDto=accountService.createAccount(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccountDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable @Valid Long id){
        AccountDto getAccountById=accountService.getAccountById(id);
        return ResponseEntity.status(HttpStatus.OK).body(getAccountById);
    }

    @GetMapping("/all")
    public List<AccountDto> getAllCustomer(){
        return accountService.getAccount();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable @Valid Long id){
        accountService.deleteAccountById(id);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(id);
    }

}
