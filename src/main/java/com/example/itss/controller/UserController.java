package com.example.itss.controller;

import com.example.itss.config.JwtUtil;
import com.example.itss.dto.UserResponseDTO;
import com.example.itss.dto.UserUpdateDTO;
import com.example.itss.model.User;
import com.example.itss.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    private Long getUserIdFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return jwtUtil.extractUserId(token);
    }
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String token, @RequestBody UserUpdateDTO userDetails) {
        try {
            String jwtToken = token.substring(7);
            Long userId = jwtUtil.extractUserId(jwtToken);

            // Find current user
            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Check if new username already exists (if changed)
            if (!existingUser.getUsername().equals(userDetails.getUsername()) &&
                    userRepository.findByUsername(userDetails.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("message", "Username already exists"));
            }

            // Update only allowed fields
            existingUser.setFullname(userDetails.getFullname());
            existingUser.setUsername(userDetails.getUsername());
            existingUser.setPhone(userDetails.getPhone());
            // Email is not updated

            User updatedUser = userRepository.save(existingUser);

            // Create DTO for response
            UserResponseDTO responseDTO = new UserResponseDTO(
                    updatedUser.getUsername(),
                    updatedUser.getEmail(),    // Original email is kept
                    updatedUser.getFullname(),
                    updatedUser.getPhone()
            );

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        try {
            Long userId = getUserIdFromToken(token);
            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Chuyển đổi User thành UserResponseDTO để không trả về password
            UserResponseDTO responseDTO = new UserResponseDTO(
//                    existingUser.getId(),
                    existingUser.getUsername(),
                    existingUser.getEmail(),
                    existingUser.getFullname(),
                    existingUser.getPhone()
            );

            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
