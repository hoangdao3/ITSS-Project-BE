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
@CrossOrigin(origins = "http://localhost:3000")
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
                return ResponseEntity.badRequest().body(Map.of("message", "メールアドレスはすでに存在します"));
            }
            if (userRepository.findByUsername(username).isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("message", "ユーザー名はすでに存在します"));
            }

            User user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));

            userRepository.save(user);
            return ResponseEntity.ok(Map.of("message", "ユーザーの登録が完了しました"));
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
                String token = jwtUtil.generateToken(user.getId());

                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("userId", user.getId());
                response.put("username", user.getUsername());
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.badRequest().body(Map.of("message", "ユーザー名またはパスワードが無効です"));
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
                    return ResponseEntity.ok(Map.of("message", "パスワードが正常に変更されました"));
                }
                return ResponseEntity.badRequest().body(Map.of("message", "古いパスワードが間違っています"));
            }
            return ResponseEntity.badRequest().body(Map.of("message", "ユーザーが見つかりません"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}