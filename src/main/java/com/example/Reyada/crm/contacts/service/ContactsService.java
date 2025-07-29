package com.example.Reyada.crm.contacts.service;

import com.example.Reyada.crm.contacts.ContactsDTO;
import com.example.Reyada.crm.contacts.data.Contact;
import com.example.Reyada.crm.contacts.data.ContactsRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ContactsService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ContactsRepo contactsRepo;

    @Autowired
    private ObjectMapper objectMapper;

    private final String webhookUrl;
    @Autowired
    public ContactsService(RestTemplate restTemplate) {
        //, @Value("${bitrix.webhook.url}") String webhookUrl
        this.restTemplate = restTemplate;
        this.webhookUrl="https://b24-0r8mng.bitrix24.com/rest/1/iolappou7w3kdu2w/crm.contact.add.json";
    }


    public String addContact (Map<String,Object> contac){
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String,Object>> request=new HttpEntity<>(contac,headers);
        Contact contact = new Contact();

        contact.setName((String) contac.get("NAME"));
        contact.setSecondName((String) contac.get("SECOND_NAME"));
        contact.setLastName((String) contac.get("LAST_NAME"));
        contact.setBirthdate(LocalDate.now());
        contactsRepo.save(contact);
        return restTemplate.postForObject(webhookUrl,request,String.class);
    }

//    public List<Contact> fetchContractsOrderedByDate() {
//
//
//        return contactsRepo.findAllByOrderByBirthdateAsc();
//    }


    public void fetchContactsFromBitrix() {


            String url ="https://b24-0r8mng.bitrix24.com/rest/1/iolappou7w3kdu2w/crm.contact.list.json";
        Map<String, Object> request = Map.of(
                "SELECT", List.of("ID", "NAME", "SECOND_NAME", "LAST_NAME", "BIRTHDATE")
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            List<Map<String, Object>> result = (List<Map<String, Object>>) response.getBody().get("result");

            List<Contact> contacts = result.stream().map(this::mapToEntity).toList();

            contactsRepo.saveAll(contacts);
        }
    }
    public List<Contact> getContacts() {
        fetchContactsFromBitrix();
        return contactsRepo.findAll();
    }

    private Contact mapToEntity(Map<String, Object> item) {
        Contact contact = new Contact();
        contact.setId(Long.valueOf((item.get("ID").toString())));
        contact.setName((String) item.get("NAME"));
        contact.setSecondName((String) item.get("SECOND_NAME"));
        contact.setLastName((String) item.get("LAST_NAME"));

        String birthdateStr = (String) item.get("BIRTHDATE");
        if (birthdateStr != null && !birthdateStr.isEmpty()) {
            try {
                contact.setBirthdate(LocalDate.parse(birthdateStr));
            } catch (DateTimeParseException e) {

                contact.setBirthdate(null);
            }
        }

        return contact;
    }
}


