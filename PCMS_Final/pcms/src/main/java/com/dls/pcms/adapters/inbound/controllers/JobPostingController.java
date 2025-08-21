package com.dls.pcms.adapters.inbound.controllers;

import com.dls.pcms.domain.models.JobPosting;
import com.dls.pcms.application.services.JobPostingService;
import com.dls.pcms.domain.models.Recruiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/job-postings")
@Slf4j
public class JobPostingController {

    @Autowired
    private JobPostingService jobPostingService;

    @PostMapping
    public ResponseEntity<JobPosting> createJobPosting(@Validated @RequestBody JobPosting jobPosting) {
        log.info("Received request to create job posting with title: {}", jobPosting.getJobTitle());

        JobPosting createdJobPosting = jobPostingService.createJobPosting(jobPosting);

        if (createdJobPosting == null) {
            log.error("Job posting creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        log.info("Job posting created successfully with ID: {}", createdJobPosting.getJobId());
        return new ResponseEntity<>(createdJobPosting, HttpStatus.CREATED);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobPosting> getJobPostingById(@PathVariable UUID jobId) {
        log.info("Received request to get job posting by ID: {}", jobId);

        Optional<JobPosting> jobPosting = jobPostingService.getJobPostingById(jobId);

        if (jobPosting.isEmpty()) {
            log.info("No job posting found with ID: {}", jobId);
            return ResponseEntity.notFound().build();
        }

        log.info("Returning job posting with ID: {}", jobId);
        return ResponseEntity.ok(jobPosting.get());
    }

    @GetMapping
    public ResponseEntity<List<JobPosting>> getAllJobPostings() {
        log.info("Received request to get all job postings");

        List<JobPosting> jobPostings = jobPostingService.getAllJobPostings();

        if (jobPostings.isEmpty()) {
            log.info("No job postings found");
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} job postings", jobPostings.size());
        return ResponseEntity.ok(jobPostings);
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<JobPosting> updateJobPosting(
            @PathVariable UUID jobId,
            @Validated @RequestBody JobPosting jobPostingDetails) {
        log.info("Received request to update job posting with ID: {}", jobId);

        JobPosting updatedJobPosting = jobPostingService.updateJobPosting(jobId, jobPostingDetails);

        if (updatedJobPosting == null) {
            log.info("Job posting update failed. No job posting found with ID: {}", jobId);
            return ResponseEntity.notFound().build();
        }

        log.info("Job posting updated successfully with ID: {}", jobId);
        return ResponseEntity.ok(updatedJobPosting);
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> deleteJobPosting(@PathVariable UUID jobId) {
        log.info("Received request to delete job posting with ID: {}", jobId);

        jobPostingService.deleteJobPosting(jobId);

        log.info("Job posting deleted successfully with ID: {}", jobId);
        return ResponseEntity.noContent().build();
    }

  @GetMapping("/recruiter/{recruiterId}")
    public ResponseEntity<List<JobPosting>> getJobPostingsByRecruiterId(@PathVariable Recruiter recruiterId) {
        log.info("Received request to get job postings by recruiter ID: {}", recruiterId);

        List<JobPosting> jobPostings = jobPostingService.getJobPostingsByRecruiterId(recruiterId);

        if (jobPostings.isEmpty()) {
            log.info("No job postings found for recruiter ID: {}", recruiterId);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} job postings for recruiter ID: {}", jobPostings.size(), recruiterId);
        return ResponseEntity.ok(jobPostings);
    }

}
