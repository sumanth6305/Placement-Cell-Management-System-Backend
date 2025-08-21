package com.dls.pcms.infrastructure.repositories;

import com.dls.pcms.application.services.FeedbackService;
import com.dls.pcms.domain.models.Feedback;
import com.dls.pcms.domain.models.JobApplication;
import com.dls.pcms.domain.models.Student;
import com.dls.pcms.domain.models.User;
import com.dls.pcms.domain.repository.FeedbackRepository;

import com.dls.pcms.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;
    private UserRepository userRepository;
    @Override
    public Feedback createFeedback(Feedback feedback) {
        log.info("Creating new feedback with type: {}", feedback.getType());
        Feedback savedFeedback = feedbackRepository.save(feedback);
        log.info("Feedback created successfully with ID: {}", savedFeedback.getFeedbackId());
        return savedFeedback;
    }



    @Override
    public Optional<Feedback> getFeedbackById(UUID feedbackId) {
        log.info("Fetching feedback by ID: {}", feedbackId);
        Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);
        if (feedback.isPresent()) {
            log.info("Feedback found with ID: {}", feedbackId);
        } else {
            log.info("No feedback found with ID: {}", feedbackId);
        }
        return feedback;
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        log.info("Fetching all feedbacks");
        List<Feedback> feedbacks = feedbackRepository.findAll();
        log.info("Found {} feedbacks", feedbacks.size());
        return feedbacks;
    }

    @Override
    public Feedback updateFeedback(UUID feedbackId, Feedback feedbackDetails) {
        log.info("Updating feedback with ID: {}", feedbackId);
        Optional<Feedback> existingFeedbackOpt = feedbackRepository.findById(feedbackId);

        if (existingFeedbackOpt.isPresent()) {
            Feedback existingFeedback = existingFeedbackOpt.get();
            existingFeedback.setType(feedbackDetails.getType());
            existingFeedback.setComments(feedbackDetails.getComments());
            existingFeedback.setRating(feedbackDetails.getRating());
            existingFeedback.setUpdatedAt(feedbackDetails.getUpdatedAt());

            Feedback updatedFeedback = feedbackRepository.save(existingFeedback);
            log.info("Feedback updated successfully with ID: {}", feedbackId);
            return updatedFeedback;
        } else {
            log.error("No feedback found with ID: {}", feedbackId);
            return null;
        }
    }

    @Override
    public void deleteFeedback(UUID feedbackId) {
        log.info("Deleting feedback with ID: {}", feedbackId);
        feedbackRepository.deleteById(feedbackId);
        log.info("Feedback deleted successfully with ID: {}", feedbackId);
    }

    @Override
    public List<Feedback> getFeedbacksByType(String type) {
        log.info("Fetching feedbacks with type: {}", type);
        List<Feedback> feedbacks = feedbackRepository.findByType(type);
        log.info("Found {} feedbacks with type: {}", feedbacks.size(), type);
        return feedbacks;
    }

    @Override
    public List<Feedback> getFeedbacksByUserId(UUID userId) {
        log.info("Fetching feedbacks with user ID: {}", userId);
        List<Feedback> feedbacks = feedbackRepository.findByUserUserId(userId);
        log.info("Found {} feedbacks with user ID: {}", feedbacks.size(), userId);
        return feedbacks;
    }

    @Override
    public List<Feedback> getFeedbacksByRating(Integer rating) {
        log.info("Fetching feedbacks with rating: {}", rating);
        List<Feedback> feedbacks = feedbackRepository.findByRating(rating);
        log.info("Found {} feedbacks with rating: {}", feedbacks.size(), rating);
        return feedbacks;
    }

    @Override
    public List<Feedback> getFeedbacksByUserIdAndType(UUID userId, String type) {
        log.info("Fetching feedbacks with user ID: {} and type: {}", userId, type);
        List<Feedback> feedbacks = feedbackRepository.findByUserIdAndType(userId, type);
        log.info("Found {} feedbacks with user ID: {} and type: {}", feedbacks.size(), userId, type);
        return feedbacks;
    }


    @Override
    public Page<Feedback> getAllFeedbacksPage(Pageable pageable){
        Page<Feedback> feedbacks = feedbackRepository.findAll(pageable);
        return feedbacks;
    }

}
