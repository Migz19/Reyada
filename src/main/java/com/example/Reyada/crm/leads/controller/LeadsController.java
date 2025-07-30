package com.example.Reyada.crm.leads.controller;

import com.example.Reyada.crm.leads.service.LeadsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crm/leads")
public class LeadsController {
    // This controller will handle all lead-related requests
    // You can define methods here to handle specific endpoints, e.g., create, update, delete leads
        @Autowired
    private LeadsServices leadsServices;
    // Example method to get all leads (to be implemented)
     @GetMapping("")
     public ResponseEntity<String> getAllLeads() {
         leadsServices.fetchLeads();

         return ResponseEntity.ok("fetched");
     }
}
