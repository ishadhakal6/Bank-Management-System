package com.example.bankmanagementsystem.model.dto;

import com.example.bankmanagementsystem.model.service.AccountNumberGenerator;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AccountDto {

    private Long number;
    @NotNull(message = "Give the account type.")
    private String type;
    private LocalDate date;
    @NotNull(message = "Customer id should not be empty")
    @Min(message = "Customer id should not be zero and negative", value = 1)
    private Long customerId;
    @NotNull
    private Long bankId;

    public long generateAccountNumber() {
        this.number = Long.valueOf(AccountNumberGenerator.generateAccountNumber());
        return number;
    }
}
