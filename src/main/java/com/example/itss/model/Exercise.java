package com.example.itss.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "start_time", nullable = false)
    private java.sql.Timestamp startTime;

    @Column(name = "end_time", nullable = false)
    private java.sql.Timestamp endTime;

    @Column(name = "exercise_type", nullable = false)
    @Enumerated(EnumType.STRING) // Dùng enum để đại diện cho 3 loại exercise
    private ExerciseType exerciseType;

    @OneToOne(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private WorkExercise workExercise;

    @OneToOne(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private YourExercise yourExercise;

    @OneToOne(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private HintExercise hintExercise;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }

    public WorkExercise getWorkExercise() {
        return workExercise;
    }

    public void setWorkExercise(WorkExercise workExercise) {
        this.workExercise = workExercise;
    }

    public YourExercise getYourExercise() {
        return yourExercise;
    }

    public void setYourExercise(YourExercise yourExercise) {
        this.yourExercise = yourExercise;
    }

    public HintExercise getHintExercise() {
        return hintExercise;
    }

    public void setHintExercise(HintExercise hintExercise) {
        this.hintExercise = hintExercise;
    }
}