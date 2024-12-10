package com.example.itss.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${secretKey}")
    private String secretKey;

    /**
     * Tạo JWT token cho username
     * @param username Tên người dùng
     * @return JWT token
     */
    public String generateToken(String username) {
        Instant now = Instant.now();
        Instant expirationTime = now.plus(10, ChronoUnit.HOURS);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Trích xuất tên người dùng từ JWT token
     * @param token JWT token
     * @return Tên người dùng
     */
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Kiểm tra tính hợp lệ của token
     * @param token JWT token
     * @return true nếu token hợp lệ, false nếu không hợp lệ
     */
    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    /**
     * Kiểm tra token có hết hạn hay không
     * @param token JWT token
     * @return true nếu token đã hết hạn, false nếu chưa hết hạn
     */
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    /**
     * Lấy các claim từ token
     * @param token JWT token
     * @return Claims chứa các thông tin trong token
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
