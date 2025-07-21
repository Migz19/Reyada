package com.example.Reyada.crm.contacts.service;

import com.example.Reyada.crm.contacts.data.Contact;
import com.example.Reyada.crm.contacts.data.ContactsRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
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

    public List<Contact> getAllContacts() {

        List<Map<String, Object>> bitrixContacts = fetchContactsFromBitrix();


        List<Contact> contacts = new ArrayList<>();
        for (Map<String, Object> data : bitrixContacts) {
            Contact contact = new Contact();
            contact.setId(Long.valueOf(data.get("ID").toString()));
            contact.setName((String) data.get("NAME"));
            contact.setLastName((String) data.get("LAST_NAME"));

            contact.setSecondName((String) data.get("SECOND_NAME"));
            contacts.add(contact);
        }


        contactsRepo.saveAll(contacts);


        return contactsRepo.findAll();
    }


    private List<Map<String, Object>> fetchContactsFromBitrix() {
        List<Map<String, Object>> allContacts = new ArrayList<>();
        int start = 0;

        while (true) {
            String url = "https://b24-0r8mng.bitrix24.com/rest/1/iolappou7w3kdu2w/crm.contact.list.json";
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            Map<String, Object> body = response.getBody();

            if (body == null || !body.containsKey("result")) break;

            List<Map<String, Object>> result = (List<Map<String, Object>>) body.get("result");
            if (result.isEmpty()) break;

            allContacts.addAll(result);

            if (!body.containsKey("next")) break;
            start = (int) body.get("next");
        }

        return allContacts;
    }
}

