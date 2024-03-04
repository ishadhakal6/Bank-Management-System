package com.example.bankmanagementsystem.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="FixedDepositAccount")
public class FixedDepositAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_no")
    private Long accountNumber;

    @Column(name = "balance",nullable = false)
    private Double balance;

    @Column(name = "tenure")
    @Enumerated(EnumType.STRING)
    private TenureType tenure;

    @Column(name = "interestRate")
    private Double interestRate;

    @Column(name = "creationTime")
    private LocalDate creationTime;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;
}
