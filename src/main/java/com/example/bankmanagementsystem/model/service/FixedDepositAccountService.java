package com.example.bankmanagementsystem.model.service;

import com.example.bankmanagementsystem.model.dto.FixedDepositAccountDto;
import com.example.bankmanagementsystem.model.entity.*;
import com.example.bankmanagementsystem.model.exception.AccountIdNotFoundException;
import com.example.bankmanagementsystem.model.exception.BankNotFoundException;
import com.example.bankmanagementsystem.model.mapper.FixedDepositAccountMapper;
import com.example.bankmanagementsystem.model.repository.BankRepository;
import com.example.bankmanagementsystem.model.repository.FixedDepositAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.bankmanagementsystem.model.mapper.FixedDepositAccountMapper.mapToFixedAccountDto;

@Service
@Transactional
public class FixedDepositAccountService {

    private final FixedDepositAccountRepository fixedDepositAccountRepository;

    private final CustomerService customerService;

    private final BankRepository bankRepository;


    public FixedDepositAccountService(FixedDepositAccountRepository fixedDepositAccountRepository, CustomerService customerService, BankRepository bankRepository) {
        this.fixedDepositAccountRepository = fixedDepositAccountRepository;
        this.customerService = customerService;
        this.bankRepository = bankRepository;
    }

    public FixedDepositAccountDto createFixedDepositAccount(FixedDepositAccountDto fixedDepositAccountDto) {
        FixedDepositAccount tempAccount = getByAccountNumber(fixedDepositAccountDto.getAccountNumber());
        FixedDepositAccountDto fixedDepositAccountDto1=null;
        if (tempAccount == null) {
            Customer customer = customerService.getCustomerById(fixedDepositAccountDto.getCustomer_id());
            Bank bank = bankRepository.findById(fixedDepositAccountDto.getBank_id()).orElseThrow(()-> new BankNotFoundException("Could not find bank"));
           FixedDepositAccount fixedDepositAccount = FixedDepositAccountMapper.mapToFixedDepositAccount(fixedDepositAccountDto, customer, bank);
            fixedDepositAccount.setBalance(0.0);
            TenureType TenureType = fixedDepositAccount.getTenure();
            if(TenureType == TenureType.SixMonths){
            fixedDepositAccount.setInterestRate(5.0);
            }else {
                fixedDepositAccount.setInterestRate(10.0);
            }
            fixedDepositAccount = fixedDepositAccountRepository.save(fixedDepositAccount);

            fixedDepositAccountDto1 = mapToFixedAccountDto(fixedDepositAccount);
            return fixedDepositAccountDto1;
        }
        return fixedDepositAccountDto1;
    }

    public FixedDepositAccount getByAccountNumber(Long accountNumber) {
        return fixedDepositAccountRepository.findByAccountNumber(accountNumber);
    }

    public FixedDepositAccountDto getAccountById(Long Id){
        FixedDepositAccount fixedDepositAccount=fixedDepositAccountRepository.findById(Id).orElseThrow(()-> new AccountIdNotFoundException("Account not found with Id:"+ Id));
        FixedDepositAccountDto fixedDepositAccountDto=mapToFixedAccountDto(fixedDepositAccount);

        return fixedDepositAccountDto;
    }

    public List<FixedDepositAccountDto> getAccount() {
        List<FixedDepositAccount> accountList = fixedDepositAccountRepository.findAll();
        List<FixedDepositAccountDto> accountDtoList = new ArrayList<>();
        for (FixedDepositAccount fixedDepositAccount :accountList) {
            FixedDepositAccountDto fixedDepositAccountDto = mapToFixedAccountDto(fixedDepositAccount);
            accountDtoList.add(fixedDepositAccountDto);
        }
        return accountDtoList;
    }

    public void deleteById(Long id){
        FixedDepositAccount fixedDepositAccount=fixedDepositAccountRepository.findById(id).orElseThrow(()-> new AccountIdNotFoundException("Account not found with this ID:" +id));
        fixedDepositAccountRepository.delete(fixedDepositAccount);
    }



}
