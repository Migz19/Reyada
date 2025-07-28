package com.example.Reyada.crm.contacts;

import com.example.Reyada.crm.contacts.data.Contact;

import java.util.ArrayList;

public class ContactsDTO {
    private Integer next;
    private ArrayList<Contact>result;

    public ContactsDTO() {
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }

    public ArrayList<Contact>  getResult() {
        return result;
    }

    public void setResult(ArrayList<Contact> result) {
        this.result = result;
    }
}
