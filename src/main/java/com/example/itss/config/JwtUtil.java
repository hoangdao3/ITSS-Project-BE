package com.example.itss.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${secretKey}")
    private String secretKey;

    private static final long EXPIRATION_TIME = 86400000L; // 24 hours
    private SecretKey key;

    @PostConstruct
    public void init() {
        if (secretKey == null || secretKey.length() < 32) {
            key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        } else {
            key = Keys.hmacShaKeyFor(secretKey.getBytes());
        }
    }

    public Long extractUserId(String token) {
        Claims claims = getClaims(token);
        return Long.parseLong(claims.getSubject());
    }

    public String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
