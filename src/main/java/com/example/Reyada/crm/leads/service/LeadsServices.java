package com.example.Reyada.crm.leads.service;

import com.example.Reyada.crm.leads.data.LeadsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LeadsServices {

//    @Autowired
//    private LeadsRepo leadsRepo;

    @Autowired
    private RestTemplate restTemplate;

//    public LeadsServices(LeadsRepo leadsRepo) {
//        this.leadsRepo = leadsRepo;
//    }

    public LeadsServices() {
    }

    public void fetchLeads(){
        //bitrix.leads.webhook
        //https://b24-0r8mng.bitrix24.com/rest/13/gedo3c1enc0vjwdf/crm.lead.list.json
        String url = "https://b24-0r8mng.bitrix24.com/rest/13/gedo3c1enc0vjwdf/crm.lead.list.json"; // Replace with actual API endpoint
        try {
            String response = restTemplate.getForObject(url, String.class);
            System.out.println(" 23423423Fetched Leads: " + response);
            // Process the response and save leads to the database if needed
        } catch (Exception e) {
            System.err.println("Error fetching leads: " + e.getMessage());
        }
    }
}
