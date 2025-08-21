package com.dls.pcms.application.services;

import com.dls.pcms.domain.models.Feedback;
import com.dls.pcms.domain.models.JobApplication;
import com.dls.pcms.domain.models.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FeedbackService {

    Feedback createFeedback(Feedback feedback);

    //Feedback createFeedback(UUID userId, String type, String comments, Integer rating);

    Optional<Feedback> getFeedbackById(UUID feedbackId);

    List<Feedback> getAllFeedbacks();

    Feedback updateFeedback(UUID feedbackId, Feedback feedbackDetails);

    void deleteFeedback(UUID feedbackId);

    List<Feedback> getFeedbacksByType(String type);

    List<Feedback> getFeedbacksByUserId(UUID userId);

    List<Feedback> getFeedbacksByRating(Integer rating);

    List<Feedback> getFeedbacksByUserIdAndType(UUID userId, String type); // Custom method
    Page<Feedback> getAllFeedbacksPage(Pageable pageable);
    // Add more custom methods as needed
}
