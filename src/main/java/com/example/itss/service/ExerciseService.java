package com.example.itss.service;

import com.example.itss.model.Exercise;
import com.example.itss.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public Exercise createExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    public Optional<Exercise> getExerciseById(Long id) {
        return exerciseRepository.findById(id);
    }

    public Exercise updateExercise(Long id, Exercise updatedExercise) {
        return exerciseRepository.findById(id).map(exercise -> {
            exercise.setStartTime(updatedExercise.getStartTime());
            exercise.setEndTime(updatedExercise.getEndTime());
            exercise.setExerciseType(updatedExercise.getExerciseType());
            return exerciseRepository.save(exercise);
        }).orElseThrow(() -> new RuntimeException("Exercise not found with id " + id));
    }

    public void deleteExercise(Long id) {
        if (!exerciseRepository.existsById(id)) {
            throw new RuntimeException("Exercise not found with id " + id);
        }
        exerciseRepository.deleteById(id);
    }
}
