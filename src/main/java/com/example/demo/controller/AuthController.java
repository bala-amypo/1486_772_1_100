package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth") // 👈 COMMON BASE PATH UPDATED HERE
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    // LOGIN: POST /auth/login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );

        User u = userService.findByEmail(req.getEmail());
        String token = jwtUtil.generateToken(u.getId(), u.getEmail(), u.getRole());

        return ResponseEntity.ok(new LoginResponse(token));
    }

    // REGISTER: POST /auth/register
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest req) {

        User saved = userService.registerUser(
            new User(null, req.getFullName(), req.getEmail(), req.getPassword(), "MONITOR")
        );

        String token = jwtUtil.generateToken(saved.getId(), saved.getEmail(), saved.getRole());

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
