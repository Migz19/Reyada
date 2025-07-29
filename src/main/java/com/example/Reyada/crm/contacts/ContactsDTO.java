package com.example.Reyada.crm.contacts;

import com.example.Reyada.crm.contacts.data.Contact;

import java.util.ArrayList;

public class ContactsDTO {
    private Long ID;
    private String NAME;
    private String SECOND_NAME;
    private String LAST_NAME;
    private String BIRTHDATE;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getSECOND_NAME() {
        return SECOND_NAME;
    }

    public void setSECOND_NAME(String SECOND_NAME) {
        this.SECOND_NAME = SECOND_NAME;
    }

    public String getLAST_NAME() {
        return LAST_NAME;
    }

    public void setLAST_NAME(String LAST_NAME) {
        this.LAST_NAME = LAST_NAME;
    }

    public String getBIRTHDATE() {
        return BIRTHDATE;
    }

    public void setBIRTHDATE(String BIRTHDATE) {
        this.BIRTHDATE = BIRTHDATE;
    }

    public ContactsDTO() {
    }}
