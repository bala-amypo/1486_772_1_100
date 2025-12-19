package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.exceptionhandler.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(User user) {

        // ✅ PUT IT HERE (business validation)
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ValidationException("Email already in use");
        }

        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
