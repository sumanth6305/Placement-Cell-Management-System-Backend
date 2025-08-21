package com.dls.pcms.adapters.inbound.controllers;

import com.dls.pcms.application.dto.GrievanceDTO;
import com.dls.pcms.application.services.GrievanceService;
import com.dls.pcms.domain.models.Feedback;
import com.dls.pcms.application.services.FeedbackService;
import com.dls.pcms.domain.models.Grievance;
import com.dls.pcms.domain.models.JobApplication;
import com.dls.pcms.domain.models.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/feedbacks")
@Slf4j
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private GrievanceService grievanceService;

    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@Validated @RequestBody Feedback feedback) {
        log.info("Received request to create feedback with type: {}", feedback.getType());

        Feedback createdFeedback = feedbackService.createFeedback(feedback);

        if (createdFeedback == null) {
            log.error("Feedback creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        log.info("Feedback created successfully with ID: {}", createdFeedback.getFeedbackId());
        return new ResponseEntity<>(createdFeedback, HttpStatus.CREATED);
    }

    @GetMapping("/{feedbackId}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable UUID feedbackId) {
        log.info("Received request to get feedback by ID: {}", feedbackId);

        Optional<Feedback> feedback = feedbackService.getFeedbackById(feedbackId);

        if (feedback.isEmpty()) {
            log.info("No feedback found with ID: {}", feedbackId);
            return ResponseEntity.notFound().build();
        }

        log.info("Returning feedback with ID: {}", feedbackId);
        return ResponseEntity.ok(feedback.get());
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        log.info("Received request to get all feedbacks");

        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();

        if (feedbacks.isEmpty()) {
            log.info("No feedbacks found");
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} feedbacks", feedbacks.size());
        return ResponseEntity.ok(feedbacks);
    }

    @PutMapping("/{feedbackId}")
    public ResponseEntity<Feedback> updateFeedback(
            @PathVariable UUID feedbackId,
            @Validated @RequestBody Feedback feedbackDetails) {
        log.info("Received request to update feedback with ID: {}", feedbackId);

        Feedback updatedFeedback = feedbackService.updateFeedback(feedbackId, feedbackDetails);

        if (updatedFeedback == null) {
            log.info("Feedback update failed. No feedback found with ID: {}", feedbackId);
            return ResponseEntity.notFound().build();
        }

        log.info("Feedback updated successfully with ID: {}", feedbackId);
        return ResponseEntity.ok(updatedFeedback);
    }

    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable UUID feedbackId) {
        log.info("Received request to delete feedback with ID: {}", feedbackId);

        feedbackService.deleteFeedback(feedbackId);

        log.info("Feedback deleted successfully with ID: {}", feedbackId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Feedback>> getFeedbacksByType(@PathVariable String type) {
        log.info("Received request to get feedbacks by type: {}", type);

        List<Feedback> feedbacks = feedbackService.getFeedbacksByType(type);

        if (feedbacks.isEmpty()) {
            log.info("No feedbacks found with type: {}", type);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} feedbacks with type: {}", feedbacks.size(), type);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Feedback>> getFeedbacksByUserId(@PathVariable UUID userId) {
        log.info("Received request to get feedbacks with user ID: {}", userId);

        List<Feedback> feedbacks = feedbackService.getFeedbacksByUserId(userId);

        if (feedbacks.isEmpty()) {
            log.info("No feedbacks found with user ID: {}", userId);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} feedbacks with user ID: {}", feedbacks.size(), userId);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<Feedback>> getFeedbacksByRating(@PathVariable Integer rating) {
        log.info("Received request to get feedbacks with rating: {}", rating);

        List<Feedback> feedbacks = feedbackService.getFeedbacksByRating(rating);

        if (feedbacks.isEmpty()) {
            log.info("No feedbacks found with rating: {}", rating);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} feedbacks with rating: {}", feedbacks.size(), rating);
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/user/{userId}/type/{type}")
    public ResponseEntity<List<Feedback>> getFeedbacksByUserIdAndType(
            @PathVariable UUID userId,
            @PathVariable String type) {
        log.info("Received request to get feedbacks with user ID: {} and type: {}", userId, type);

        List<Feedback> feedbacks = feedbackService.getFeedbacksByUserIdAndType(userId, type);

        if (feedbacks.isEmpty()) {
            log.info("No feedbacks found with user ID: {} and type: {}", userId, type);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} feedbacks with user ID: {} and type: {}", feedbacks.size(), userId, type);
        return ResponseEntity.ok(feedbacks);
    }

    @PostMapping("/grievances")
    public ResponseEntity<GrievanceDTO> uploadGrievance(@RequestParam("file") MultipartFile file,
                                                        @RequestParam("studentId") UUID studentId,
                                                        @RequestParam("description") String description,
                                                        @RequestParam("subject") String subject) throws Exception {
        log.info("Handling request to upload grievance for studentId: {}", studentId);

        Grievance grievance = grievanceService.saveGrievance(file, studentId, description, subject);
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/grievances/download/")
                .path(grievance.getGrievanceId().toString())
                .toUriString();

        GrievanceDTO grievanceDTO = new GrievanceDTO(
                grievance.getGrievanceId(),
                grievance.getStudent().getStudentId(),
                grievance.getSubject(),
                grievance.getDescription(),
                grievance.getStatus(),
                grievance.getResumeFilename()
        );

        log.info("Grievance uploaded successfully with ID: {}", grievance.getGrievanceId());
        log.info("Download URL: {}", downloadURL);

        return new ResponseEntity<>(grievanceDTO, HttpStatus.CREATED);
    }

    @GetMapping("/grievances/{grievanceId}")
    public ResponseEntity<Page<Grievance>> getAllGrievancesPage(@PathVariable UUID grievanceId,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        Grievance grievance = grievanceService.findById(grievanceId);
        if (grievance == null) {
            return ResponseEntity.notFound().build();
        }
        log.info("Received request to get job applications with pagination - Page: {}, Size: {}", page, size);
        //log.info("Received request to get all feedbacks");
        Pageable pageable = PageRequest.of(page, size);
        Page<Grievance> grievances = grievanceService.getAllGrievancesPage(pageable);

        if (grievances.isEmpty()) {
            log.info("No interview schedules found");
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} interview schedules", grievances.getTotalElements());
        return ResponseEntity.ok(grievances);
    }

    @GetMapping("/download/grievances/{grievanceId}")
    public ResponseEntity<Resource> downloadGrievance(@PathVariable UUID grievanceId) throws Exception {
        log.info("Handling request to download grievance with ID: {}", grievanceId);
        Grievance grievance = grievanceService.getGrievance(grievanceId);
        log.info("Grievance {} found for download", grievanceId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + grievance.getResumeFilename() + "\"")
                .body(new ByteArrayResource(grievance.getResumeFile()));
    }

    @DeleteMapping("/grievances/{grievanceId}")
    public ResponseEntity<Void> deleteGrievance(@PathVariable UUID grievanceId) throws Exception {
        log.info("Handling request to delete grievance with ID: {}", grievanceId);
        grievanceService.deleteGrievance(grievanceId);
        log.info("Grievance {} deleted successfully", grievanceId);
        return ResponseEntity.noContent().build();
    }



}
