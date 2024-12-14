package com.example.itss.controller;

import com.example.itss.config.JwtUtil;
import com.example.itss.dto.ChangePasswordDTO;
import com.example.itss.model.User;
import com.example.itss.repository.UserRepository;
import com.example.itss.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Map<String, String> register(@RequestBody Map<String, String> userData) {
        // Lấy thông tin từ userData
        String email = userData.get("email");
        String username = userData.get("username");
        String password = userData.get("password");

        // Kiểm tra xem email và username đã tồn tại chưa
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email đã tồn tại");
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username đã tồn tại");
        }

        // Tạo một đối tượng User mới
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Mã hóa mật khẩu

        // Lưu người dùng vào cơ sở dữ liệu
        userRepository.save(user);

        return Map.of("message", "User  registered successfully");
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent() && passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
            // Tạo JWT Token khi đăng nhập thành công
            String token = jwtUtil.generateToken(existingUser.get().getUsername());  // Gọi phương thức non-static
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("token", token);  // Gửi token cùng với phản hồi
            return response;
        }
        throw new RuntimeException("Invalid username or password");
    }


    @PostMapping("/logout")
    public Map<String, String> logout() {
        return Map.of("message", "Logout successful");
    }

    @PostMapping("/forgot-password")
    public Map<String, String> forgotPassword(@RequestParam String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            String resetLink = "http://example.com/reset-password?token=some_token";
            emailService.sendEmail(email, "Password Reset", "Here is your password reset link: " + resetLink);
            return Map.of("message", "Password reset email sent successfully");
        }
        return Map.of("message", "Email not found");
    }

    @PostMapping("/change-password")
    public Map<String, String> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        Optional<User> user = userRepository.findByUsername(changePasswordDTO.getUsername());
        if (user.isPresent()) {
            if (passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.get().getPassword())) {
                user.get().setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
                userRepository.save(user.get());
                return Map.of("message", "Password changed successfully");
            } else {
                return Map.of("message", "Old password is incorrect");
            }
        }
        return Map.of("message", "User not found");
    }

}
