package com.example.bankmanagementsystem.model.service;

import java.util.Random;

public class AccountNumberGenerator {
    private static final int ACCOUNT_NUMBER_LENGTH = 10;
    private static final String ALLOWED_CHARS = "0123456789";

    public static String generateAccountNumber() {
        StringBuilder sb = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARS.length());
            char randomChar = ALLOWED_CHARS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
