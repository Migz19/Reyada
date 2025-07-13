package com.example.Reyada.authentication.config;

public class LoginResponse {

    private int id;
    private String name;
    private String email;

    public LoginResponse(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters only (no setters needed unless needed)
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}
