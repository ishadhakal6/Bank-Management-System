package com.example.bankmanagementsystem.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferTransactionDto {

    @NotNull
    private Double amount;

    private String type;


    private Long sourceAccountNumber;

    private Long destinationAccountNumber;


}
