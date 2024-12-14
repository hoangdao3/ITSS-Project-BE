package com.example.itss.controller;

import com.example.itss.model.WorkExercise;
import com.example.itss.service.WorkExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/work-exercises")
public class WorkExerciseController {

    private final WorkExerciseService workExerciseService;

    @Autowired
    public WorkExerciseController(WorkExerciseService workExerciseService) {
        this.workExerciseService = workExerciseService;
    }

    @PostMapping
    public WorkExercise createWorkExercise(@RequestBody WorkExercise workExercise) {
        return workExerciseService.createWorkExercise(workExercise);
    }

    @GetMapping
    public List<WorkExercise> getAllWorkExercises() {
        return workExerciseService.getAllWorkExercises();
    }

    @GetMapping("/{id}")
    public Optional<WorkExercise> getWorkExerciseById(@PathVariable Long id) {
        return workExerciseService.getWorkExerciseById(id);
    }

    @PutMapping("/{id}")
    public WorkExercise updateWorkExercise(@PathVariable Long id, @RequestBody WorkExercise workExercise) {
        return workExerciseService.updateWorkExercise(id, workExercise);
    }

    @DeleteMapping("/{id}")
    public void deleteWorkExercise(@PathVariable Long id) {
        workExerciseService.deleteWorkExercise(id);
    }
}
