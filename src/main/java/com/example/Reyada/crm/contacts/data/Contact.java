package com.example.Reyada.crm.contacts.data;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "contacts")
public class Contact {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "second_name")
    private String secondName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birthdate")
    private LocalDate birthdate;

    public Contact(Long id, String name, String secondName, String lastName, LocalDate birthdate) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.lastName = lastName;
        this.birthdate = birthdate;
    }
    public Contact() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", secondName='" + secondName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
}
