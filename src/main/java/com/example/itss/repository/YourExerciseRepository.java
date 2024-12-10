package com.example.itss.repository;

import com.example.itss.model.YourExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YourExerciseRepository extends JpaRepository<YourExercise, Long> {
}