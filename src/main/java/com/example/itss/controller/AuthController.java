package com.example.itss.controller;

import com.example.itss.config.JwtUtil;
import com.example.itss.dto.ChangePasswordDTO;
import com.example.itss.model.User;
import com.example.itss.repository.UserRepository;
import com.example.itss.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> userData) {
        try {
            String email = userData.get("email");
            String username = userData.get("username");
            String password = userData.get("password");

            if (userRepository.findByEmail(email).isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("message", "Email already exists"));
            }
            if (userRepository.findByUsername(username).isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("message", "Username already exists"));
            }

            User user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));

            userRepository.save(user);
            return ResponseEntity.ok(Map.of("message", "User registered successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        try {
            String username = loginRequest.get("username");
            String password = loginRequest.get("password");

            Optional<User> userOpt = userRepository.findByUsername(username);
            if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
                User user = userOpt.get();
                String token = jwtUtil.generateToken(user.getUsername(), user.getId());

                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("userId", user.getId());
                response.put("username", user.getUsername());
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid username or password"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        try {
            Optional<User> userOpt = userRepository.findByUsername(changePasswordDTO.getUsername());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
                    user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
                    userRepository.save(user);
                    return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
                }
                return ResponseEntity.badRequest().body(Map.of("message", "Old password is incorrect"));
            }
            return ResponseEntity.badRequest().body(Map.of("message", "User not found"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}