package com.example.bankmanagementsystem.model.dto;

import lombok.Data;

@Data
public class TransactionResponseDto {

    private Double amount;
    private String type;
    private Long paymentId;
    private Long accountId;

    private Long traceId;
    private String transactionStatus;
}
