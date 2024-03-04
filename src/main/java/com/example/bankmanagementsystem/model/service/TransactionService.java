package com.example.bankmanagementsystem.model.service;


import com.example.bankmanagementsystem.model.dto.*;
import com.example.bankmanagementsystem.model.entity.Account;
import com.example.bankmanagementsystem.model.entity.FixedDepositAccount;
import com.example.bankmanagementsystem.model.entity.Transaction;
import com.example.bankmanagementsystem.model.entity.TransactionType;
import com.example.bankmanagementsystem.model.exception.AccountNumberNotFoundException;
import com.example.bankmanagementsystem.model.exception.IdNotFoundException;
import com.example.bankmanagementsystem.model.exception.InsufficientFundException;
import com.example.bankmanagementsystem.model.mapper.TransactionMapper;
import com.example.bankmanagementsystem.model.repository.AccountRepository;
import com.example.bankmanagementsystem.model.repository.FixedDepositAccountRepository;
import com.example.bankmanagementsystem.model.repository.TransactionRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;


    private final FixedDepositAccountRepository fixedDepositAccountRepository;


    private final WebClient webClient;

    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository, FixedDepositAccountRepository fixedDepositAccountRepository, WebClient webClient) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.fixedDepositAccountRepository = fixedDepositAccountRepository;
        this.webClient = webClient;
    }


    public void withdrawAmount(TransactionDto transactionDto) {
        Transaction transaction = TransactionMapper.mapToTransaction(transactionDto);
        Long accountId = transactionDto.getAccountId();
        double amount = transactionDto.getAmount();

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IdNotFoundException("Account not found for ID: " + accountId));


        double currentBalance = account.getBalance();
        double newBalance = currentBalance - amount;

        account.setBalance(newBalance);


        accountRepository.save(account);


        transaction.setAmount(amount);
        transaction.setType(TransactionType.WITHDRAWAL);
        transaction.setAccount(account);
        //transaction.setId(accountId);

        transactionRepository.save(transaction);
    }

    public void depositAmount(TransactionDto transactionDto, WebClientTransactionDto webClientTransactionDto) {
        Transaction transaction = TransactionMapper.mapToTransaction(transactionDto);
        Long accountId = transactionDto.getAccountId();
        double amount = transactionDto.getAmount();


        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IdNotFoundException("Account not found for ID: " + accountId));

        double currentBalance = account.getBalance();
        double newBalance = currentBalance + amount;
        account.setBalance(newBalance);

        accountRepository.save(account);


        transaction.setAmount(amount);
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setAccount(account);
        transaction.setTime(LocalDateTime.now());
        transaction.setTraceId(webClientTransactionDto.getTraceId());
        transaction.setPaymentId(webClientTransactionDto.getPaymentId());
        transaction.setTransactionStatus(webClientTransactionDto.getTransactionStatus());

        transactionRepository.save(transaction);

//        FixedDepositAccount fixedDepositAccount=fixedDepositAccountRepository.findById(accountId)
//                .orElseThrow(()-> new AccountIdNotFoundException("Account1 not found with ID:" + accountId));
//
//        fixedDepositAccount.setBalance(newBalance);

    }

    public void transferAmount(TransferTransactionDto transferTransactionDto, FixedDepositAccountDto fixedDepositAccountDto) {
        Transaction transaction = TransactionMapper.mapToTransaction(transferTransactionDto);

        Account sourceAccount = accountRepository.findByNumber(transferTransactionDto.getSourceAccountNumber());

        Account destinationAccount = accountRepository.findByNumber(transferTransactionDto.getDestinationAccountNumber());

        FixedDepositAccount fixedDepositAccount = fixedDepositAccountRepository.findByAccountNumber(fixedDepositAccountDto.getAccountNumber());

        Long sourceAccountNumber = sourceAccount.getNumber();
        Long destinationAccountNumber = destinationAccount.getNumber();
        Long fixedDepositAccountNumber = fixedDepositAccount.getAccountNumber();

        if (!isValidAccountNumber(sourceAccountNumber, sourceAccount.getCustomer().getId())) {
            throw new AccountNumberNotFoundException("Invalid source account number.");
        }

        if (!isValidAccountNumber(destinationAccountNumber, destinationAccount.getCustomer().getId())) {
            throw new AccountNumberNotFoundException("Invalid destination account number.");
        }
        if (!isValidAccountNumber(fixedDepositAccountNumber, fixedDepositAccount.getCustomer().getId())) {
            throw new AccountNumberNotFoundException("Invalid fixedDeposit account number.");
        }


        double transferAmount = transferTransactionDto.getAmount();


        if (sourceAccount.getBalance() < transferAmount) {
            throw new InsufficientFundException("Insufficient balance in the source account.");
        }

        // Update account balances
        double newSourceBalance = sourceAccount.getBalance() - transferAmount;
        double newDestinationBalance = destinationAccount.getBalance() + transferAmount;
        double newFixedDepositBalance = fixedDepositAccount.getBalance();

        sourceAccount.setBalance(newSourceBalance);
        destinationAccount.setBalance(newDestinationBalance);
        fixedDepositAccount.setBalance(newFixedDepositBalance);


        // Save updated accounts
        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);
        fixedDepositAccountRepository.save(fixedDepositAccount);

        // Set the transaction details
        transaction.setType(TransactionType.TRANSFER);
        transaction.setAccount(sourceAccount);
        transaction.setTime(LocalDateTime.now());
        transactionRepository.save(transaction);


    }

    private boolean isValidAccountNumber(Long accountNumber, Long customerId) {
        // Retrieve the account from the repository using the account number
        Account account = accountRepository.findByNumber(accountNumber);

        // If no account found, or the account does not belong to the specified customer, return false
        if (account == null || !account.getCustomer().getId().equals(customerId)) {
            return false;
        }

        // Otherwise, the account number is valid for the specified customer
        return true;
    }


    public List<Transaction> getTransactionHistory(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    public TransactionResponseDto makeTransaction(WebClientTransactionDto webClientTransactionDto) {
        String apiUrl = "http://localhost:8081/payment";

        // Make the POST request
        TransactionResponseDto response = webClient.post()
                .uri(apiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(webClientTransactionDto)
                .retrieve()
                .bodyToMono(TransactionResponseDto.class)
                .block();

        Long accountId = webClientTransactionDto.getAccountId();
        Optional<Account> optAccount = accountRepository.findById(accountId);
        Transaction transaction = TransactionMapper.mapToTransaction(response, optAccount.get());
        transaction.setTraceId(response.getTraceId());
        transaction.setTransactionStatus(response.getTransactionStatus());
        transaction.setPaymentId(response.getPaymentId());
        transactionRepository.save(transaction);

        return response;

    }




//    public Flux<TransactionDto> getByTransactionType(String type) {
//        return webClient.get()
//                .uri("http://localhost:8081/pay:"+type)
//                .retrieve()
//                .bodyToFlux(TransactionDto.class);
//    }
}


