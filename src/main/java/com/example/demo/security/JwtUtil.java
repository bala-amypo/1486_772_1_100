package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    // ✅ NAME MATCHES CONFIG (@Value jwt.secret)
    private final String secret;

    // ✅ NAME MATCHES CONFIG (@Value jwt.expiration)
    private final long expiration;

    // ✅ REQUIRED constructor (used by SecurityConfig)
    public JwtUtil(String secret, long expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    // ✅ USED DURING LOGIN
    public String generateToken(String email) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // ✅ USED BY JwtAuthenticationFilter
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ✅ USED BY JwtAuthenticationFilter
    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
