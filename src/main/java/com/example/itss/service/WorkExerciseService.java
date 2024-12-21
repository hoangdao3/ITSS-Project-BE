//package com.example.itss.service;
//
//import com.example.itss.model.WorkExercise;
//import com.example.itss.repository.WorkExerciseRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class WorkExerciseService {
//
//    private final WorkExerciseRepository workExerciseRepository;
//
//    @Autowired
//    public WorkExerciseService(WorkExerciseRepository workExerciseRepository) {
//        this.workExerciseRepository = workExerciseRepository;
//    }
//
//    public WorkExercise createWorkExercise(WorkExercise workExercise) {
//        return workExerciseRepository.save(workExercise);
//    }
//
//    public List<WorkExercise> getAllWorkExercises() {
//        return workExerciseRepository.findAll();
//    }
//
//    public Optional<WorkExercise> getWorkExerciseById(Long id) {
//        return workExerciseRepository.findById(id);
//    }
//
//    public WorkExercise updateWorkExercise(Long id, WorkExercise updatedWorkExercise) {
//        return workExerciseRepository.findById(id).map(workExercise -> {
//            workExercise.setDescription(updatedWorkExercise .getDescription());
//            workExercise.setNote(updatedWorkExercise.getNote());
//            return workExerciseRepository.save(workExercise);
//        }).orElseThrow(() -> new RuntimeException("WorkExercise not found with id " + id));
//    }
//
//    public void deleteWorkExercise(Long id) {
//        if (!workExerciseRepository.existsById(id)) {
//            throw new RuntimeException("WorkExercise not found with id " + id);
//        }
//        workExerciseRepository.deleteById(id);
//    }
//}
