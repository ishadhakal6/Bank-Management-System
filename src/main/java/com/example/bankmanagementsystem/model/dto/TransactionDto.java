package com.example.bankmanagementsystem.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransactionDto {

    @NotNull
    private double amount;

    @NotNull
    private String type;

    @NotNull
    private Long accountId;

    @NotNull
    private Integer traceId;

    @NotNull
    private String transactionStatus;

    @NotNull
    private Long paymentId;


}
