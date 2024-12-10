package com.example.itss.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
}