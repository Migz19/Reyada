package com.example.Reyada.crm.leads.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LeadsListResponse {
    @JsonProperty("result") private LeadsResult result;
    public LeadsResult getResult() { return result; }
    public void setResult(LeadsResult result) { this.result = result; }
    public static class LeadsResult {
        @JsonProperty("leads") private List<LeadDto> leads;
        public List<LeadDto> getLeads() { return leads; }
        public void setLeads(List<LeadDto> leads) { this.leads = leads; }
    }
}

