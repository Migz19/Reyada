package com.example.Reyada.crm.contacts;

import com.example.Reyada.crm.contacts.data.Contact;
import com.example.Reyada.crm.contacts.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

       List<Contact> saved = contactsService.fetchContractsOrderedByDate();
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/addContact")
    public ResponseEntity<String> addContact(@RequestBody Map<String, Object> contactData) {

        return ResponseEntity.ok(contactsService.addContact(contactData));


    }
}

