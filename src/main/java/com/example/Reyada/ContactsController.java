package com.example.Reyada;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("crm")
public class ContactsController {
    @Autowired
    private ContactsService contactsService;

    public ContactsController(ContactsService contactsService) {
        this.contactsService = contactsService;
    }
    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getContacts() {

       List<Contact> saved = contactsService.getAllContacts();
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/addContact")
    public ResponseEntity<String> addContact(@RequestBody Map<String, Object> contactData) {

        return ResponseEntity.ok(contactsService.addContact(contactData));


    }
}

