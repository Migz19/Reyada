package com.example.Reyada.crm.contacts.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactsRepo extends JpaRepository<Contact, Long>{
        List<Contact> findAllByOrderByBirthdateAsc(); // Method to find all contacts ordered by name in ascending order
}
