package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {

    User registerUser(User user);      // 🔥 REQUIRED
    User findByEmail(String email);    // 🔥 REQUIRED
    User getById(Long id);
}
