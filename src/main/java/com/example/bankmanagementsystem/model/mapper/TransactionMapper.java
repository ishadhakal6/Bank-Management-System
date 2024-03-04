package com.example.bankmanagementsystem.model.mapper;

import com.example.bankmanagementsystem.model.dto.TransactionDto;
import com.example.bankmanagementsystem.model.dto.TransactionResponseDto;
import com.example.bankmanagementsystem.model.dto.TransferTransactionDto;
import com.example.bankmanagementsystem.model.dto.WebClientTransactionDto;
import com.example.bankmanagementsystem.model.entity.Account;
import com.example.bankmanagementsystem.model.entity.Transaction;
import com.example.bankmanagementsystem.model.entity.TransactionType;

import java.time.LocalDateTime;

public class TransactionMapper {

    public static TransactionDto mapToTransactionDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();

        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setType(String.valueOf(transaction.getType()));
        transactionDto.setAccountId(transaction.getAccount().getId());
        return transactionDto;
    }

    public static Transaction mapToTransaction(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.getAmount());
        transaction.setType(TransactionType.valueOf(transactionDto.getType()));
        transaction.setTime(LocalDateTime.now());
        return transaction;
    }

    public static TransferTransactionDto mapToTransferTransactionDto(Transaction transaction) {
        TransferTransactionDto transferTransactionDto = new TransferTransactionDto();
        transferTransactionDto.setAmount(transaction.getAmount());
        transferTransactionDto.setType(String.valueOf(transaction.getType()));
        transferTransactionDto.setSourceAccountNumber(transaction.getId());
        transferTransactionDto.setDestinationAccountNumber(transaction.getId());
        return transferTransactionDto;
    }

    public static Transaction mapToTransaction(TransferTransactionDto transferTransactionDto) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transferTransactionDto.getAmount());
        transaction.setType(TransactionType.valueOf(transferTransactionDto.getType()));

        return transaction;
    }

    public static WebClientTransactionDto mapToWebClientTransactionDto(Transaction transaction) {
        WebClientTransactionDto webClientTransactionDto=new WebClientTransactionDto();

        webClientTransactionDto.setAmount(transaction.getAmount());
        webClientTransactionDto.setType(String.valueOf(transaction.getType()));
        webClientTransactionDto.setAccountId(transaction.getAccount().getId());
        webClientTransactionDto.setPaymentId(transaction.getPaymentId());
        return webClientTransactionDto;
    }

    public static Transaction mapToTransaction(TransactionResponseDto transactionResponseDto, Account account) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionResponseDto.getAmount());
        transaction.setType(TransactionType.valueOf(transactionResponseDto.getType()));
        transaction.setTime(LocalDateTime.now());
        transaction.setTransactionStatus(transactionResponseDto.getTransactionStatus());
        transaction.setAccount(account);
        transaction.setTraceId(transactionResponseDto.getTraceId());
        return transaction;
    }
}
