package com.example.Reyada.crm.deals.dto;

import com.example.Reyada.crm.deals.data.Deal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DealResponse {
    @JsonProperty("result")
    private Deal[] result;

    public Deal[] getResult() { return result; }
}

