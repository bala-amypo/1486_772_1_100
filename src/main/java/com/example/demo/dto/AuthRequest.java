package com.example.demo.dto;

public class AuthRequest {

    private String email;
    private String password;

    // No-arg constructor
    public AuthRequest() {
    }

    // Parameterized constructor (optional but safe)
    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters & Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
}
