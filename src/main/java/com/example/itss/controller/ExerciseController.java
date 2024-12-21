package com.example.itss.controller;

import com.example.itss.config.JwtUtil;
import com.example.itss.dto.AddExercise;
import com.example.itss.model.*;
import com.example.itss.repository.*;
import com.example.itss.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

//    @Autowired
//    private WorkExerciseRepository workExerciseRepository;
//
//    @Autowired
//    private YourExerciseRepository yourExerciseRepository;
//
//    @Autowired
//    private HintExerciseRepository hintExerciseRepository;

    @Autowired
    private ExerciseService exerciseService;

    // Lấy userId từ token
    private Long getUserIdFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return jwtUtil.extractUserId(token);
    }
    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<Void> deleteExercise(@RequestHeader("Authorization") String token, @PathVariable Long exerciseId) {
        Long userId = getUserIdFromToken(token); // Lấy userId từ token
        Exercise existingExercise = exerciseRepository.findByIdAndUserId(exerciseId, userId)
                .orElseThrow(() -> new RuntimeException("Exercise not found or not owned by the user"));

        exerciseRepository.delete(existingExercise); // Xóa bài tập khỏi cơ sở dữ liệu
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Trả về status 204 khi xóa thành công
    }

//    @PostMapping("/add")
//    public ResponseEntity<String> addExercise(@RequestHeader("Authorization") String token,
//                                              @RequestBody AddExercise exerciseRequest) {
//        Long userId = getUserIdFromToken(token);
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User  not found"));
//
//        Exercise exercise = new Exercise();
//        exercise.setUserId(userId); // Sử dụng userId thay vì đối tượng User
//        exercise.setStartTime(Timestamp.valueOf(exerciseRequest.getStartTime()));
//        exercise.setEndTime(Timestamp.valueOf(exerciseRequest.getEndTime()));
//        exercise.setType(ExerciseType.valueOf(exerciseRequest.getExerciseType()));
//
//        exercise = exerciseRepository.save(exercise);
//
//        switch (exercise.getType()) {
//            case WORK:
//                WorkExercise workExercise = new WorkExercise();
//                workExercise.setExercise(exercise);
//                workExercise.setDescription(exerciseRequest.getDescription());
//                workExerciseRepository.save(workExercise);
//                break;
//            case YOUR:
//                YourExercise yourExercise = new YourExercise();
//                yourExercise.setExercise(exercise);
//                yourExercise.setName(exerciseRequest.getName());
//                yourExercise.setDescription(exerciseRequest.getDescription());
//                yourExerciseRepository.save(yourExercise);
//                break;
//            case HINT:
//                HintExercise hintExercise = new HintExercise();
//                hintExercise.setExercise(exercise);
//                hintExercise.setHint(exerciseRequest.getHint());
//                hintExerciseRepository.save(hintExercise);
//                break;
//        }
//
//        return ResponseEntity.status(HttpStatus.CREATED).body("Exercise added successfully");
//    }

    @PostMapping
    public ResponseEntity<Exercise> createExercise(@RequestHeader("Authorization") String token, @RequestBody AddExercise addExercise) {
        Long userId = getUserIdFromToken(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Exercise exercise = new Exercise();
        exercise.setUserId(userId);

        // Convert LocalDateTime directly to Timestamp
        exercise.setStartTime(Timestamp.valueOf(addExercise.getStartTime()));
        exercise.setEndTime(Timestamp.valueOf(addExercise.getEndTime()));
        exercise.setType(addExercise.getExerciseType());
        exercise.setDescription(addExercise.getDescription());
        exercise.setNote(addExercise.getNote());

        Exercise createdExercise = exerciseService.createExercise(exercise);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExercise);
    }
    @PutMapping("/{exerciseId}")
    public ResponseEntity<Exercise> updateExercise(
            @RequestHeader("Authorization") String token,
            @PathVariable Long exerciseId,
            @RequestBody AddExercise addExercise) {

        Long userId = getUserIdFromToken(token); // Lấy userId từ token
        Exercise existingExercise = exerciseRepository.findByIdAndUserId(exerciseId, userId)
                .orElseThrow(() -> new RuntimeException("Exercise not found or not owned by the user"));

        existingExercise.setStartTime(Timestamp.valueOf(addExercise.getStartTime())); // Cập nhật thời gian bắt đầu
        existingExercise.setEndTime(Timestamp.valueOf(addExercise.getEndTime())); // Cập nhật thời gian kết thúc
        existingExercise.setType(addExercise.getExerciseType());
        existingExercise.setDescription(addExercise.getDescription());
        existingExercise.setNote(addExercise.getNote());

        Exercise updatedExercise = exerciseService.createExercise(existingExercise); // Lưu lại bài tập đã sửa
        return ResponseEntity.status(HttpStatus.OK).body(updatedExercise); // Trả về bài tập đã sửa
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Exercise>> getAllExercises(@RequestHeader("Authorization") String token) {
        Long userId = getUserIdFromToken(token);
        List<Exercise> exercises = exerciseRepository.findByUserId(userId);
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Long userId = getUserIdFromToken(token);
        Exercise exercise = exerciseService.getExerciseByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));
        return ResponseEntity .ok(exercise);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Exercise> updateExercise(@RequestHeader("Authorization") String token, @PathVariable Long id, @RequestBody Exercise exercise) {
//        Long userId = getUserIdFromToken(token);
//        exercise.setUserId(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User  not found")));
//        Exercise updatedExercise = exerciseService.updateExercise(id, exercise);
//        return ResponseEntity.ok(updatedExercise);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteExercise(@RequestHeader("Authorization") String token, @PathVariable Long id) {
//        Long userId = getUserIdFromToken(token);
//        exerciseService.deleteExerciseByIdAndUserId(id, userId);
//        return ResponseEntity.ok("Exercise deleted successfully");
//    }
}