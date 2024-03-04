package com.example.bankmanagementsystem.model.exception;

public class CustomerNotFoundException extends  RuntimeException{
    public CustomerNotFoundException(String customerNotFound) {
        super(customerNotFound);
    }
}
