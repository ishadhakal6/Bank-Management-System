package com.example.bankmanagementsystem.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebClientBankDto {
    @JsonProperty("place_id")
    private int placeId;
    @JsonProperty("display_name")
    private String displayName;

}
