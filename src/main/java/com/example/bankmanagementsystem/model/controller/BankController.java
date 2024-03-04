package com.example.bankmanagementsystem.model.controller;

import com.example.bankmanagementsystem.model.dto.BankDto;
import com.example.bankmanagementsystem.model.dto.WebClientBankDto;
import com.example.bankmanagementsystem.model.entity.Bank;
import com.example.bankmanagementsystem.model.service.BankService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api/bank")
public class BankController {

    private final BankService bankService;


    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping
    public ResponseEntity<BankDto> insertBank(@RequestBody @Valid BankDto bankDto){
        BankDto createdBankDto=bankService.insertBank(bankDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBankDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankDto> getBank(@PathVariable @Valid Long id){
        BankDto getBankById= bankService.getBankById(id);
        return ResponseEntity.status(HttpStatus.OK).body(getBankById);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBank(@PathVariable @Valid Long id){
        bankService.deleteBankById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(id);
    }

    @GetMapping("/{pageNo}/{pageSize}")
    public List<Bank> getPaginatedBank(@PathVariable int pageNo, @PathVariable int pageSize){

        return bankService.findPaginated(pageNo, pageSize);
    }

//    @GetMapping("/openapi/{name}")
//    public Flux<String> getByBankName(@PathVariable String name){
//        return bankService.getBankByName(name);
//    }

    @GetMapping("/openapi/{name}")
    public Flux<WebClientBankDto> getByBankName(@PathVariable String name){
        return bankService.getBankByName(name);
    }

    @PostMapping("/openapi")
    public ResponseEntity<String> createBank(@RequestParam("name") String bankName,
                                             @RequestParam("location") String location) {
        bankService.createBank(bankName, location);
        return ResponseEntity.ok("Bank created successfully");
    }
}
