package com.example.bankmanagementsystem.model.controller;

import com.example.bankmanagementsystem.model.dto.FixedDepositAccountDto;
import com.example.bankmanagementsystem.model.dto.TransactionDto;
import com.example.bankmanagementsystem.model.entity.FixedDepositAccount;
import com.example.bankmanagementsystem.model.service.FixedDepositAccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/bank/fixed-deposit")
public class FixedDepositAccountController {

    private final FixedDepositAccountService fixedDepositAccountService;

    public FixedDepositAccountController(FixedDepositAccountService fixedDepositAccountService) {
        this.fixedDepositAccountService = fixedDepositAccountService;
    }

    @PostMapping("/create")
    public ResponseEntity<FixedDepositAccountDto> createAccount(@RequestBody FixedDepositAccountDto fixedDepositAccountDto){
        FixedDepositAccountDto fixedDepositAccountDto1=fixedDepositAccountService.createFixedDepositAccount(fixedDepositAccountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(fixedDepositAccountDto1);
    }

    @GetMapping("/all")
    public ResponseEntity<FixedDepositAccountDto> getAllAccount(@RequestBody FixedDepositAccountDto fixedDepositAccountDto){
        fixedDepositAccountService.getAccount();
        return ResponseEntity.status(HttpStatus.OK).body(fixedDepositAccountDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        fixedDepositAccountService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(id);
    }

//    @PostMapping("/deposit")
//    public ResponseEntity<String> depositAmount(@RequestBody @Valid FixedDepositAccountDto fixedDepositAccountDto) {
//        fixedDepositAccountService.depositAmount(fixedDepositAccountDto);
//        return new ResponseEntity<>("Deposit successful", HttpStatus.OK);
//    }

}
