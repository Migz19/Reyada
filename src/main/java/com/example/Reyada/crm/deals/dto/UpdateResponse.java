package com.example.Reyada.crm.deals.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public  class UpdateResponse {
    @JsonProperty("result")
    public static Boolean result;

}

