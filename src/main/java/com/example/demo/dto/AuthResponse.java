package com.example.demo.dto;

public class AuthResponse {

    private String token;
    private String type;

    public AuthResponse() {
        this.type = "Bearer";
    }

    public AuthResponse(String token) {
        this.token = token;
        this.type = "Bearer";
    }

    public String getToken() { return token; }
    public String getType() { return type; }

    public void setToken(String token) { this.token = token; }
    public void setType(String type) { this.type = type; }
}
