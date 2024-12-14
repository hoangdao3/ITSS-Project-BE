package com.example.itss.controller;

import com.example.itss.model.*;
import com.example.itss.repository.*;
import com.example.itss.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private WorkExerciseRepository workExerciseRepository;

    @Autowired
    private YourExerciseRepository yourExerciseRepository;

    @Autowired
    private HintExerciseRepository hintExerciseRepository;
    @Autowired
    private ExerciseService exerciseService;

    @PostMapping("/add")
    public String addExercise(@RequestParam Long userId,
                              @RequestParam ExerciseType exerciseType,
                              @RequestParam String startTime,
                              @RequestParam String endTime,
                              @RequestParam(required = false) String description,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String hint) {
        // Tìm user theo userId
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Tạo Exercise
        Exercise exercise = new Exercise();
        exercise.setUser(user);
        exercise.setStartTime(Timestamp.valueOf(startTime));
        exercise.setEndTime(Timestamp.valueOf(endTime));
        exercise.setExerciseType(exerciseType);

        // Lưu Exercise vào cơ sở dữ liệu
        exercise = exerciseRepository.save(exercise);

        // Thêm thông tin chi tiết tùy thuộc vào loại bài tập
        switch (exerciseType) {
            case WORK:
                WorkExercise workExercise = new WorkExercise();
                workExercise.setExercise(exercise);
                workExercise.setDescription(description);
                workExerciseRepository.save(workExercise);
                break;
            case YOUR:
                YourExercise yourExercise = new YourExercise();
                yourExercise.setExercise(exercise);
                yourExercise.setName(name);
                yourExercise.setDescription(description);
                yourExerciseRepository.save(yourExercise);
                break;
            case HINT:
                HintExercise hintExercise = new HintExercise();
                hintExercise.setExercise(exercise);
                hintExercise.setHint(hint);
                hintExerciseRepository.save(hintExercise);
                break;
        }

        return "Exercise added successfully";
    }
    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping
    public Exercise createExercise(@RequestBody Exercise exercise) {
        return exerciseService.createExercise(exercise);
    }

    @GetMapping
    public List<Exercise> getAllExercises() {
        return exerciseService.getAllExercises();
    }

    @GetMapping("/{id}")
    public Optional<Exercise> getExerciseById(@PathVariable Long id) {
        return exerciseService.getExerciseById(id);
    }

    @PutMapping("/{id}")
    public Exercise updateExercise(@PathVariable Long id, @RequestBody Exercise exercise) {
        return exerciseService.updateExercise(id, exercise);
    }

    @DeleteMapping("/{id}")
    public void deleteExercise(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
    }
}
