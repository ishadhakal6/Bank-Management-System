package com.example.bankmanagementsystem.model.exception;

public class AccountNumberNotFoundException extends RuntimeException {
    public AccountNumberNotFoundException(String sourceAccountNumber) {
        super(sourceAccountNumber);
    }
}
