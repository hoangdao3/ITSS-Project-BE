package com.example.itss.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.itss.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}