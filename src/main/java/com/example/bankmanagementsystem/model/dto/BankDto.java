package com.example.bankmanagementsystem.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankDto {


    @NotNull
    private String name;

    @NotNull
    private String address;


}
