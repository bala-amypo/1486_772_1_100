package com.example.demo.dto;

public class AuthResponse {

    private String token;
    private String email;
    private String role;
    private Long userId;

    // ✅ REQUIRED
    public AuthResponse() {}

    // ✅ REQUIRED BY TEST (4-ARG CONSTRUCTOR)
    public AuthResponse(String token, long userId, String email, String role) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.role = role;
    }

    // ✅ REQUIRED (OLDER CONSTRUCTOR)
    public AuthResponse(String token, String email, String role) {
        this.token = token;
        this.email = email;
        this.role = role;
    }

    // ✅ REQUIRED GETTERS (TEST USES THESE)
    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public Long getUserId() {
        return userId;
    }
}
