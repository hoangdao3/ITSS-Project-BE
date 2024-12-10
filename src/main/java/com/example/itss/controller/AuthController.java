package com.example.itss.controller;

import com.example.itss.dto.AuthRequest;
import com.example.itss.dto.RegisterRequest;
import com.example.itss.dto.UserResponse;
import com.example.itss.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @GetMapping("/user-info")
    public UserResponse getUserInfo(@RequestHeader("Authorization") String token) {
        String username = token.replace("Bearer ", "");
        return authService.getUserInfo(username);
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestHeader("Authorization") String token, @RequestBody String newPassword) {
        String username = token.replace("Bearer ", "");
        return authService.changePassword(username, newPassword);
    }
}
