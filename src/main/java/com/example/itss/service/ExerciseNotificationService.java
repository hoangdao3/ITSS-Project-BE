//package com.example.itss.service;
//
//import com.example.itss.model.Exercise;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//public class ExerciseNotificationService {
//
//    private final ExerciseRepository exerciseRepository;
//    private final NotificationService notificationService;
//
//    public ExerciseNotificationService(ExerciseRepository exerciseRepository, NotificationService notificationService) {
//        this.exerciseRepository = exerciseRepository;
//        this.notificationService = notificationService;
//    }
//
//    @Scheduled(fixedRate = 60000) // Kiểm tra mỗi 1 phút
//    public void checkAndSendNotifications() {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime thirtyMinutesBeforeEndTime = now.plusMinutes(30);
//
//        List<Exercise> exercises = exerciseRepository.findAll();
//        for (Exercise exercise : exercises) {
//            if (exercise.getEndTime().isBefore(thirtyMinutesBeforeEndTime) && exercise.getEndTime().isAfter(now)) {
//                // Gửi thông báo nếu còn 30 phút đến giờ kết thúc
//                notificationService.sendNotification(exercise);
//            }
//        }
//    }
//}
