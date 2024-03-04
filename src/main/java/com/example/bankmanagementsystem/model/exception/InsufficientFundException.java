package com.example.bankmanagementsystem.model.exception;

public class InsufficientFundException extends RuntimeException {
    public InsufficientFundException(String s) {
        super(s);
    }
}
