package com.example.bankmanagementsystem.model.service;


import com.example.bankmanagementsystem.model.dto.BankDto;
import com.example.bankmanagementsystem.model.dto.WebClientBankDto;
import com.example.bankmanagementsystem.model.entity.Bank;
import com.example.bankmanagementsystem.model.exception.IdNotFoundException;
import com.example.bankmanagementsystem.model.mapper.BankMapper;
import com.example.bankmanagementsystem.model.repository.BankRepository;
import jakarta.transaction.Transactional;
import jdk.jfr.ContentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.net.http.HttpHeaders.*;

@Service
@Transactional
public class BankService {

    private final BankRepository bankRepository;

    private final WebClient webClient;

    public BankService(BankRepository bankRepository, WebClient webClient) {
        this.bankRepository = bankRepository;
        this.webClient = webClient;
    }

    public BankDto insertBank(BankDto bankDto) {
        Bank tempBank = getBankByBankName(bankDto.getName());
        BankDto bankDto1 = null;
        if (tempBank == null) {
            Bank bank = BankMapper.mapToBank(bankDto);
            bank = bankRepository.save(bank);

            bankDto1 = BankMapper.mapToBankDto(bank);
            return bankDto1;
        }
        return bankDto1;
    }

    public BankDto getBankById(Long id) {
        Bank bank = bankRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Bank not found with this ID:" + id));
        BankDto bankDto = BankMapper.mapToBankDto(bank);

        return bankDto;
    }

    private Bank getBankByBankName(String name) {
        return bankRepository.findByName(name);
    }

    public void deleteBankById(Long id) {
        Bank bank = bankRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Bank not found with this ID:" + id));
        bankRepository.delete(bank);
    }

    public List<Bank> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Bank> pagedResult = bankRepository.findAll(pageable);
        return pagedResult.toList();

    }

//    public Flux<String> getBankByName(String name) {
//        return webClient.get()
//                .uri("https://nominatim.openstreetmap.org/search?q=" + name + "&format=json")
//                .retrieve()
//                .bodyToFlux(String.class);
//
//    }

    public Flux<WebClientBankDto> getBankByName(String name) {
        return webClient.get()
                .uri("https://nominatim.openstreetmap.org/search?q=" + name + "&format=json")
                .retrieve()
                .bodyToFlux(WebClientBankDto.class);

    }

    public void createBank(String bankName, String location) {
        String apiUrl = "https://jsonplaceholder.typicode.com/posts";

        // Create the request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("title", bankName);
        requestBody.put("body", "Location: " + location);

        // Make the POST request
        webClient.post()
                .uri(apiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}
