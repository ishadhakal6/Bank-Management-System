package com.example.bankmanagementsystem.model.exception;

public class BankNotFoundException extends RuntimeException {
    public BankNotFoundException(String couldNotFindBank) {
        super(couldNotFindBank);
    }
}
