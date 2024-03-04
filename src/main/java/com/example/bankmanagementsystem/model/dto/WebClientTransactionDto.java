package com.example.bankmanagementsystem.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebClientTransactionDto {
    @NotNull
    private Double amount;
    @NotNull
    private String type;
    @NotNull
    private Long accountId;
    private Long traceId;
    private String transactionStatus;
    @NotNull
    private Long paymentId;

}
