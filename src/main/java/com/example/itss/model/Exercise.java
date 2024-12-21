package com.example.itss.model;

import com.example.itss.model.ExerciseType;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "exercises2")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId; // Thay vì sử dụng đối tượng User, bạn có thể sử dụng userId

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "exercise_type")
    private ExerciseType exercise_type; // Sử dụng enum để đại diện cho các loại exercise

    @Column(name = "description")
    private String description;

    @Column(name = "note")
    private String note;

    // Getter và Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public ExerciseType getType() {
        return exercise_type;
    }

    public void setType(ExerciseType type) {
        this.exercise_type = type;
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