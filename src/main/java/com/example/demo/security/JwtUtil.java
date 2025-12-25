package com.example.demo.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {

    private final String secret = "SECRET_KEY"; // replace with env/config
    private final long validityInMs = 3600000; // 1 hour

    public String generateToken(Authentication authentication, Long userId, String email, String role) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("email", email)
                .claim("role", role)
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validityInMs))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Long getUserIdFromToken(String token) {
        return ((Number) Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody().get("userId")).longValue();
    }

    public String getRoleFromToken(String token) {
        return (String) Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody().get("role");
    }
}
