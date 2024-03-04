package com.example.bankmanagementsystem.model.dto;

import com.example.bankmanagementsystem.model.entity.Address;
import com.example.bankmanagementsystem.model.entity.ContactInfo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {

    private Long id;

    @NotBlank
    private String name;


    private Address address;


    private ContactInfo contactInfo;

    @NotNull
    private String street;

    @NotNull
    private String city;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    private String email;

    private String phoneNumber;
}

