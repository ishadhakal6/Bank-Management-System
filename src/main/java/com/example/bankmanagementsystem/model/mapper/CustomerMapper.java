package com.example.bankmanagementsystem.model.mapper;

import com.example.bankmanagementsystem.model.dto.CustomerDto;
import com.example.bankmanagementsystem.model.entity.Customer;

import java.util.List;

public class CustomerMapper {
    public static Customer mapToEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setAddress(customerDto.getAddress());
        customer.setContactInfo(customerDto.getContactInfo());
        return customer;
    }

    public static CustomerDto mapToDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(customer.getName());
        customerDto.setContactInfo(customer.getContactInfo());
        customerDto.setAddress(customer.getAddress());
        return customerDto;
    }

}
