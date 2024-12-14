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

    private static final long EXPIRATION_TIME = 86400000L;

    // Sử dụng Keys để tạo khoá bí mật có độ dài đủ cho HS384 hoặc HS512
    private static SecretKey key;

    @PostConstruct
    public void init() {
        if (secretKey != null && !secretKey.isEmpty()) {
            key = Keys.hmacShaKeyFor(secretKey.getBytes());
        } else {
            // Tạo một khoá bí mật ngẫu nhiên nếu không có giá trị từ application.properties
            key = Keys.secretKeyFor(SignatureAlgorithm.HS384); // Hoặc HS512 nếu bạn muốn
        }
    }

    // Phương thức non-static để tạo token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 60 * 1000)) // 10 giờ
                .signWith(key) // Sử dụng khoá đã được tạo từ Keys
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }
}
