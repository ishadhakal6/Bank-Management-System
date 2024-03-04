package com.example.bankmanagementsystem.model.repository;

import com.example.bankmanagementsystem.model.entity.Transaction;
import com.example.bankmanagementsystem.model.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.nio.channels.FileChannel;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByAccountId(Long accountId);

    FileChannel findByTraceId(int traceId);

    List<Transaction> findByType(TransactionType transactionType);
}
