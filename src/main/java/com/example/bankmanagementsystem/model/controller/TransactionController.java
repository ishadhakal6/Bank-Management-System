package com.example.bankmanagementsystem.model.controller;

import com.example.bankmanagementsystem.model.dto.*;
import com.example.bankmanagementsystem.model.entity.Transaction;
import com.example.bankmanagementsystem.model.service.FixedDepositAccountService;
import com.example.bankmanagementsystem.model.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/bank/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    private final FixedDepositAccountService fixedDepositAccountService;

    @Autowired
    public TransactionController(TransactionService transactionService, FixedDepositAccountService fixedDepositAccountService) {
        this.transactionService = transactionService;
        this.fixedDepositAccountService = fixedDepositAccountService;
    }

    @PostMapping("/withdraw")

    public ResponseEntity<String> withdrawAmount(@RequestBody @Valid TransactionDto transactionDto) {
        transactionService.withdrawAmount(transactionDto);
        return new ResponseEntity<>("Withdrawal successful", HttpStatus.OK);
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> depositAmount(@RequestBody @Valid WebClientTransactionDto webClientTransactionDto, @RequestBody TransactionDto transactionDto) {
        transactionService.depositAmount(transactionDto, webClientTransactionDto);
        return new ResponseEntity<>("Deposit successful", HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferAmount(@RequestBody @Valid TransferTransactionDto transferTransactionDto, FixedDepositAccountDto fixedDepositAccountDto) {
        transactionService.transferAmount(transferTransactionDto, fixedDepositAccountDto);
        return new ResponseEntity<>("Transfer successful", HttpStatus.OK);
    }

    @GetMapping("/history/{accountId}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable @Valid Long accountId) {
        List<Transaction> transactionHistory = transactionService.getTransactionHistory(accountId);
        return new ResponseEntity<>(transactionHistory, HttpStatus.OK);
    }

    @PostMapping("/pay")
    public ResponseEntity<TransactionResponseDto> makeTransaction(@RequestBody WebClientTransactionDto webClientTransactionDto) {
        return new ResponseEntity<>(transactionService.makeTransaction(webClientTransactionDto), HttpStatus.OK);

    }


}

