package com.dls.pcms.adapters.inbound.controllers;

import com.dls.pcms.domain.models.JobApplication;
import com.dls.pcms.application.services.JobApplicationService;
import com.dls.pcms.domain.models.Student;
import com.dls.pcms.domain.models.JobPosting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/job-applications")
@Slf4j
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    @PostMapping
    public ResponseEntity<JobApplication> createJobApplication(@Validated @RequestBody JobApplication jobApplication) {
        log.info("Received request to create job application for student ID: {} and job ID: {}",
                jobApplication.getStudent().getStudentId(), jobApplication.getJobPosting().getJobId());

        JobApplication createdJobApplication = jobApplicationService.createJobApplication(jobApplication);

        if (createdJobApplication == null) {
            log.error("Job application creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }


        log.info("Job application created successfully with ID: {}", createdJobApplication.getApplicationId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{applicationId}")
    public ResponseEntity<JobApplication> getJobApplicationById(@PathVariable UUID applicationId) {
        log.info("Received request to get job application by ID: {}", applicationId);

        Optional<JobApplication> jobApplication = jobApplicationService.getJobApplicationById(applicationId);

        if (jobApplication.isEmpty()) {
            log.info("No job application found with ID: {}", applicationId);
            return ResponseEntity.notFound().build();
        }

        log.info("Returning job application with ID: {}", applicationId);
        return ResponseEntity.ok(jobApplication.get());
    }

    @GetMapping
    public ResponseEntity<List<JobApplication>> getAllJobApplications() {
        log.info("Received request to get all job applications");

        List<JobApplication> jobApplications = jobApplicationService.getAllJobApplications();

        if (jobApplications.isEmpty()) {
            log.info("No job applications found");
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} job applications", jobApplications.size());
        return ResponseEntity.ok(jobApplications);
    }

    @PutMapping("/{applicationId}")
    public ResponseEntity<JobApplication> updateJobApplication(
            @PathVariable UUID applicationId,
            @Validated @RequestBody JobApplication jobApplicationDetails) {
        log.info("Received request to update job application with ID: {}", applicationId);

        JobApplication updatedJobApplication = jobApplicationService.updateJobApplication(applicationId, jobApplicationDetails);

        if (updatedJobApplication == null) {
            log.info("Job application update failed. No job application found with ID: {}", applicationId);
            return ResponseEntity.notFound().build();
        }

        log.info("Job application updated successfully with ID: {}", applicationId);
        return ResponseEntity.ok(updatedJobApplication);
    }

    @DeleteMapping("/{applicationId}")
    public ResponseEntity<Void> deleteJobApplication(@PathVariable UUID applicationId) {
        log.info("Received request to delete job application with ID: {}", applicationId);

        jobApplicationService.deleteJobApplication(applicationId);

        log.info("Job application deleted successfully with ID: {}", applicationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<JobApplication>> getJobApplicationsByStudentId(@PathVariable Student studentId) {
        log.info("Received request to get job applications by student ID: {}", studentId);

        List<JobApplication> jobApplications = jobApplicationService.getJobApplicationsByStudentId(studentId);

        if (jobApplications.isEmpty()) {
            log.info("No job applications found for student ID: {}", studentId);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} job applications for student ID: {}", jobApplications.size(), studentId);
        return ResponseEntity.ok(jobApplications);
    }

    @GetMapping("/job-posting/{jobPostingId}")
    public ResponseEntity<List<JobApplication>> getJobApplicationsByJobPostingId(@PathVariable JobPosting jobPostingId) {
        log.info("Received request to get job applications by job posting ID: {}", jobPostingId);

        List<JobApplication> jobApplications = jobApplicationService.getJobApplicationsByJobPostingId(jobPostingId);

        if (jobApplications.isEmpty()) {
            log.info("No job applications found for job posting ID: {}", jobPostingId);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} job applications for job posting ID: {}", jobApplications.size(), jobPostingId);
        return ResponseEntity.ok(jobApplications);
    }
}
