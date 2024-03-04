package com.example.bankmanagementsystem.model.controller;

import com.example.bankmanagementsystem.model.dto.CustomerDto;
import com.example.bankmanagementsystem.model.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/insert")
    private ResponseEntity<CustomerDto> insertCustomer(@RequestBody @Valid CustomerDto customerDto) {
        CustomerDto createdCustomer = customerService.createCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @GetMapping
    private List<CustomerDto> getAllCustomer(){
    return customerService.getCustomer();
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<CustomerDto> updateCustomer(@RequestBody @Valid CustomerDto customerDto){
        CustomerDto updatedCustomer=customerService.updateCustomer(customerDto);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable @Valid Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
