package com.example.Reyada;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
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
        this.restTemplate = restTemplate;
        this.webhookUrl = "https://b24-0r8mng.bitrix24.com/rest/13/s0wmul8rocahyacy/crm.contact.add.json";
    }




//    public List<Contact> fetchAndSaveAll(String EXTERNAL_URL) {
//        ResponseEntity<JsonNode> resp = restTemplate.getForEntity(EXTERNAL_URL, JsonNode.class);
//        JsonNode root = resp.getBody();
//
//        JsonNode resultArray = root.path("result");
//
//        List<Contact> contacts = objectMapper.convertValue(
//                resultArray,
//                new TypeReference<List<Contact>>() {
//                }
//        );
//
//
//        System.out.println("3424234" + contacts.get(0).getName() + " " + contacts.get(0).getId() + " " );
//        //contactsRepo.saveAll(contacts);
////contactsRepo.findAll();
//        return contacts;
//    }
//
//
    public String addContact (Map<String,Object> contac){
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String,Object> payload= Map.of("fields",contac);
       // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
        HttpEntity<Map<String,Object>> request=new HttpEntity<>(payload,headers);
        Contact contact = new Contact();
        contact.setName((String) contac.get("NAME"));
        contact.setSecondName((String) contac.get("SECOND_NAME"));
        contact.setLastName((String) contac.get("LAST_NAME"));
        //System.out.println("ewfsdF"+contac.get("BIRTHDATE"));
        contact.setBirthdate(LocalDate.now());
        contactsRepo.save(contact);
        return restTemplate.postForObject(webhookUrl,request,String.class);
    }
    public List<Contact> getAllContacts() {
        return contactsRepo.findAll();
    }
}

