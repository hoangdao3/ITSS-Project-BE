package com.example.itss.dto;

import com.example.itss.model.ExerciseType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AddExercise {

    @NotNull(message = "User  ID cannot be null")
    private Long userId; // Có thể không cần thiết nếu bạn lấy từ token

    @NotNull(message = "Start time cannot be null")
    private LocalDateTime startTime;

    @NotNull(message = "End time cannot be null")
    private LocalDateTime endTime;

    @NotNull(message = "Exercise type cannot be null")
    private ExerciseType exerciseType; // Sử dụng enum để đại diện cho các loại exercise

    private String description;
    private String note;

    // Getter và Setter
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}