package com.example.Reyada.crm.contacts.service;

import com.example.Reyada.crm.contacts.ContactsDTO;
import com.example.Reyada.crm.contacts.data.Contact;
import com.example.Reyada.crm.contacts.data.ContactsRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

    public List<Contact> fetchContractsOrderedByDate() {



        //contactsRepo.saveAll(fetchContactsFromBitrix());


        for (
                Contact contact : fetchContactsFromBitrix()) {
            System.out.println(contact.toString());
        }

        return contactsRepo.findAllByOrderByBirthdateAsc();
    }


    private ArrayList<Contact> fetchContactsFromBitrix() {

        int start = 0;
        ArrayList<Contact> bitrixContacts = new ArrayList<>();
        do {

            String url = UriComponentsBuilder
                    .fromHttpUrl("https://b24-0r8mng.bitrix24.com/rest/1/iolappou7w3kdu2w/crm.contact.list.json")
                    .queryParam("start", start)
                    .toUriString();
            ResponseEntity<ContactsDTO> response = restTemplate.getForEntity(url, ContactsDTO.class);
            if (response.getBody().getResult() == null || response.getBody().getResult().isEmpty()) {
                break;
            }
            bitrixContacts.addAll(response.getBody().getResult());
            start = (response.getBody().getNext() == null ? 0 : response.getBody().getNext());
            System.out.println("Fetched " + response.getBody().getResult() + " contacts, next start: " + start);
        }while (start > 0);


        System.out.println(bitrixContacts.toString());
        return bitrixContacts;
    }
}

