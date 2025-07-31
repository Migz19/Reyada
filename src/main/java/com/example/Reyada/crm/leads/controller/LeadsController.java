package com.example.Reyada.crm.leads.controller;

import com.example.Reyada.crm.leads.service.LeadDto;
import com.example.Reyada.crm.leads.service.LeadsServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/crm/leads")
public class LeadsController {
    // This controller will handle all lead-related requests
    // You can define methods here to handle specific endpoints, e.g., create, update, delete leads
        @Autowired
    private LeadsServices leadsServices;
    // Example method to get all leads (to be implemented)
    // Leads endpoints
    @GetMapping("")
    public List<LeadDto> getLeads(
            @RequestParam Map<String,String> params,
            @RequestParam(defaultValue = "ASC") String sortField,
            @RequestParam(defaultValue = "ASC") String sortOrder
    ) throws JsonProcessingException {
        Map<String,Object> filter = buildFilter(params);
        Map<String,String> order = Map.of(sortField, sortOrder);
        return leadsServices.listLeads(filter, order);
    }
    private Map<String,Object> buildFilter(Map<String,String> params) {
        Map<String,Object> f = new HashMap<>();
        params.forEach((k,v) -> {
            if (k.startsWith("min")) f.put(">" + k.substring(3).toUpperCase(), v);
            else if (k.startsWith("max")) f.put("<" + k.substring(3).toUpperCase(), v);
            else f.put("=" + k.toUpperCase(), v);
        });
        return f;
    }
}
