package com.example.Reyada.crm.leads.service;

import com.example.Reyada.crm.leads.data.Lead;
import com.example.Reyada.crm.leads.data.LeadsRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.RequestEntity.post;

@Service
public class LeadsServices {

    @Autowired
    private LeadsRepo leadsRepo;

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


    // Leads
    public List<LeadDto> listLeads(Map<String,Object> filter, Map<String,String> order) throws JsonProcessingException {
        Map<String,Object> body = new HashMap<>();
        body.put("filter", filter);
        body.put("order", order);
        body.put("select", Arrays.asList(
                "ID",
                "TITLE",
                "STATUS_ID",
                "OPPORTUNITY",
                "DATE_CREATE",
                "DATE_CLOSED",
                "ASSIGNED_BY_ID"
        ));

        // Fetch raw JSON
        String url = "https://b24-0r8mng.bitrix24.com/rest/13/gedo3c1enc0vjwdf/crm.lead.list.json";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String,Object>> request = new HttpEntity<>(body, headers);
        String json = restTemplate.postForObject(url, request, String.class);

        // Parse manually because 'result' is an array
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        JsonNode arrayNode = root.path("result");

        List<LeadDto> result = new ArrayList<>();
        for (JsonNode node : arrayNode) {
            LeadDto dto = new LeadDto();
            dto.setId(node.path("ID").asLong());
            dto.setTitle(node.path("TITLE").asText(null));
            dto.setStatusId(node.path("STATUS_ID").asText(null));
            if (node.hasNonNull("OPPORTUNITY")) {
                dto.setOpportunity(new BigDecimal(node.path("OPPORTUNITY").asText()));
            }
            String createText = node.path("DATE_CREATE").asText(null);
            if (createText != null && !createText.isEmpty()) {
                dto.setDateCreate(OffsetDateTime.parse(createText).toLocalDateTime());
            }
            String closedText = node.path("DATE_CLOSED").asText(null);
            if (closedText != null && !closedText.isEmpty()) {
                dto.setDateClosed(OffsetDateTime.parse(closedText).toLocalDateTime());
            }
            dto.setAssignedById(node.path("ASSIGNED_BY_ID").asLong());
            result.add(dto);
        }
        return result;
    }


    public List<Lead> syncLeads(Map<String,Object> filter, Map<String,String> order) throws JsonProcessingException {
        List<LeadDto> dtos = listLeads(filter, order);
        List<Lead> entities = dtos.stream().map(dto -> {
            Lead e = new Lead();
            e.setId(dto.getId()); e.setTitle(dto.getTitle());
            e.setStatusId(dto.getStatusId()); e.setOpportunity(dto.getOpportunity());
            e.setDateCreate(dto.getDateCreate()); e.setDateClosed(dto.getDateClosed());
            e.setAssignedById(dto.getAssignedById());
            return e;
        }).collect(Collectors.toList());
        return leadsRepo.saveAll(entities);
    }

    public List<Lead> getOfflineLeads(){
        return leadsRepo.findAll();
    }
}
