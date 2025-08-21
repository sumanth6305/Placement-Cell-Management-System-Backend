package com.dls.pcms.adapters.inbound.controllers;

import com.dls.pcms.application.services.*;
import com.dls.pcms.domain.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/recruiters")
@Slf4j
public class RecruiterController {

    @Autowired
    private RecruiterService recruiterService;
    @Autowired
    private JobPostingService jobPostingService;
    @Autowired
    private JobApplicationService jobApplicationService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private InterviewScheduleService interviewScheduleService;
    @PostMapping
    public ResponseEntity<Recruiter> createRecruiter(@Validated @RequestBody Recruiter recruiter) {
        log.info("Received request to create recruiter with company name: {}", recruiter.getCompanyName());

        Recruiter createdRecruiter = recruiterService.createRecruiter(recruiter);

        if (createdRecruiter == null) {
            log.error("Recruiter creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        log.info("Recruiter created successfully with ID: {}", createdRecruiter.getRecruiterId());
        return new ResponseEntity<>(createdRecruiter, HttpStatus.CREATED);
    }

    @GetMapping("/{recruiterId}")
    public ResponseEntity<Recruiter> getRecruiterById(@PathVariable UUID recruiterId) {
        log.info("Received request to get recruiter by ID: {}", recruiterId);

        Optional<Recruiter> recruiter = recruiterService.getRecruiterById(recruiterId);

        if (recruiter.isEmpty()) {
            log.info("No recruiter found with ID: {}", recruiterId);
            return ResponseEntity.notFound().build();
        }

        log.info("Returning recruiter with ID: {}", recruiterId);
        return ResponseEntity.ok(recruiter.get());
    }

    @GetMapping
    public ResponseEntity<List<Recruiter>> getAllRecruiters() {
        log.info("Received request to get all recruiters");

        List<Recruiter> recruiters = recruiterService.getAllRecruiters();

        if (recruiters.isEmpty()) {
            log.info("No recruiters found");
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} recruiters", recruiters.size());
        return ResponseEntity.ok(recruiters);
    }

    @PutMapping("/{recruiterId}")
    public ResponseEntity<Recruiter> updateRecruiter(
            @PathVariable UUID recruiterId,
            @Validated @RequestBody Recruiter recruiterDetails) {
        log.info("Received request to update recruiter with ID: {}", recruiterId);

        Recruiter updatedRecruiter = recruiterService.updateRecruiter(recruiterId, recruiterDetails);

        if (updatedRecruiter == null) {
            log.info("Recruiter update failed. No recruiter found with ID: {}", recruiterId);
            return ResponseEntity.notFound().build();
        }

        log.info("Recruiter updated successfully with ID: {}", recruiterId);
        return ResponseEntity.ok(updatedRecruiter);
    }

    @DeleteMapping("/{recruiterId}")
    public ResponseEntity<Void> deleteRecruiter(@PathVariable UUID recruiterId) {
        log.info("Received request to delete recruiter with ID: {}", recruiterId);

        recruiterService.deleteRecruiter(recruiterId);

        log.info("Recruiter deleted successfully with ID: {}", recruiterId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/company/{companyName}")
    public ResponseEntity<List<Recruiter>> getRecruitersByCompanyName(@PathVariable String companyName) {
        log.info("Received request to get recruiters by company name: {}", companyName);

        List<Recruiter> recruiters = recruiterService.getRecruitersByCompanyName(companyName);

        if (recruiters.isEmpty()) {
            log.info("No recruiters found for company name: {}", companyName);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} recruiters for company name: {}", recruiters.size(), companyName);
        return ResponseEntity.ok(recruiters);
    }

    @GetMapping("/contact/{contactEmail}")
    public ResponseEntity<List<Recruiter>> getRecruitersByContactEmail(@PathVariable String contactEmail) {
        log.info("Received request to get recruiters by contact email: {}", contactEmail);

        List<Recruiter> recruiters = recruiterService.getRecruitersByContactEmail(contactEmail);

        if (recruiters.isEmpty()) {
            log.info("No recruiters found for contact email: {}", contactEmail);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} recruiters for contact email: {}", recruiters.size(), contactEmail);
        return ResponseEntity.ok(recruiters);
    }


    @PostMapping("/{recruiterId}/jobs")
    public ResponseEntity<JobPosting> createJobPosting(
            @PathVariable UUID recruiterId,
            @Validated @RequestBody JobPosting jobPosting) {

        // Ensure the recruiterId in the JobPosting matches the PathVariable
        if (!recruiterId.equals(jobPosting.getRecruiter().getRecruiterId())) {
            log.error("Mismatch between PathVariable recruiterId: {} and JobPosting recruiterId: {}",
                    recruiterId, jobPosting.getRecruiter().getRecruiterId());
            return ResponseEntity.badRequest().build();
        }

        // Find the recruiter by ID
        Recruiter recruiter = recruiterService.findById(recruiterId);
        if (recruiter == null) {
            log.error("Recruiter with ID {} not found", recruiterId);
            return ResponseEntity.notFound().build();
        }

        log.info("Received request to create job posting for recruiter ID: {} with title: {}",
                recruiterId, jobPosting.getJobTitle());

        // Associate the recruiter with the job posting
        jobPosting.setRecruiter(recruiter);
        JobPosting createdJobPosting = jobPostingService.createJobPosting(jobPosting);

        if (createdJobPosting == null) {
            log.error("Job posting creation failed for recruiter ID: {}", recruiterId);
            return ResponseEntity.badRequest().build();
        }

        log.info("Job posting created successfully with ID: {}", createdJobPosting.getJobId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @GetMapping("/{recruiterId}/jobs")
    public ResponseEntity<Page<JobPosting>> getAllJobPostings(@PathVariable UUID recruiterId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        Recruiter recruiter = recruiterService.findById(recruiterId);
        if (recruiter == null) {
            return ResponseEntity.notFound().build();
        }
        log.info("Received request to get job applications with pagination - Page: {}, Size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<JobPosting> jobPostings = jobPostingService.getAllJobPostingspage(pageable);

        if (jobPostings.isEmpty()) {
            log.info("No job postings found");
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} job postings", jobPostings.getTotalElements());
        return ResponseEntity.ok(jobPostings);
    }


//pending

    @DeleteMapping("/{recruiterId}/jobs/{jobId}")
    public ResponseEntity<Void> deleteJobPosting(@PathVariable UUID jobId) {
        log.info("Received request to delete job posting with ID: {}", jobId);

        jobPostingService.deleteJobPosting(jobId);

        log.info("Job posting deleted successfully with ID: {}", jobId);
        return ResponseEntity.noContent().build();
    }

     /*

    @DeleteMapping("/{recruiterId}/jobs/{jobId}")
    public ResponseEntity<Void> deleteJobPosting(
            @PathVariable UUID recruiterId,
            @PathVariable UUID jobId) {

        log.info("Received request to delete job posting with ID: {} for recruiter with ID: {}", jobId, recruiterId);

        // Find the recruiter by ID
        Recruiter recruiter = recruiterService.findById(recruiterId);
        if (recruiter == null) {
            log.error("Recruiter with ID {} not found", recruiterId);
            return ResponseEntity.notFound().build();
        }

        // Find the job posting by ID
        JobPosting jobPosting = jobPostingService.findById(jobId);
        if (jobPosting == null) {
            log.error("Job posting with ID {} not found", jobId);
            return ResponseEntity.notFound().build();
        }

        // Check if the job posting belongs to the recruiter
        if (!recruiterId.equals(jobPosting.getRecruiter().getRecruiterId())) {
            log.error("Mismatch between PathVariable recruiterId: {} and job posting recruiterId: {}",
                    recruiterId, jobPosting.getRecruiter().getRecruiterId());
            return ResponseEntity.badRequest().build();
        }

        // Perform the delete operation
        jobPostingService.deleteJobPosting(jobId);

        log.info("Job posting deleted successfully with ID: {} for recruiter ID: {}", jobId, recruiterId);
        return ResponseEntity.noContent().build();
    }


      */

    @PutMapping("/{recruiterId}/jobs/{jobId}")
    public ResponseEntity<JobPosting> updateJobPosting(
            @PathVariable UUID recruiterId,
            @PathVariable UUID jobId,
            @Validated @RequestBody JobPosting jobPostingDetails) {

        log.info("Received request to update job posting with ID: {}", jobId);

        // Ensure the recruiterId in the JobPosting matches the PathVariable
        if (!recruiterId.equals(jobPostingDetails.getRecruiter().getRecruiterId())) {
            log.error("Mismatch between PathVariable recruiterId: {} and JobPosting recruiterId: {}",
                    recruiterId, jobPostingDetails.getRecruiter().getRecruiterId());
            return ResponseEntity.badRequest().build();
        }

        // Find the recruiter by ID
        Recruiter recruiter = recruiterService.findById(recruiterId);
        if (recruiter == null) {
            log.error("Recruiter with ID {} not found", recruiterId);
            return ResponseEntity.notFound().build();
        }

        // Find the job posting by ID
        JobPosting existingJobPosting = jobPostingService.findById(jobId);
        if (existingJobPosting == null) {
            log.error("Job posting with ID {} not found", jobId);
            return ResponseEntity.notFound().build();
        }

        log.info("Valid job posting found. Updating job posting with ID: {}", jobId);

        // Associate the recruiter with the updated job posting details
        jobPostingDetails.setRecruiter(recruiter);

        // Perform the update operation
        JobPosting updatedJobPosting = jobPostingService.updateJobPosting(jobId, jobPostingDetails);

        if (updatedJobPosting == null) {
            log.error("Job posting update failed for job ID: {}", jobId);
            return ResponseEntity.badRequest().build();
        }

        log.info("Job posting updated successfully with ID: {}", jobId);
        return ResponseEntity.ok(updatedJobPosting);
    }




    @GetMapping("/{recruiterId}/applications")
    public ResponseEntity<Page<JobApplication>> getAllJobApplications(
            @PathVariable UUID recruiterId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // Check if the student exists
        Recruiter recruiter = recruiterService.findById(recruiterId);
        if (recruiter == null) {
            return ResponseEntity.notFound().build();
        }
        log.info("Received request to get job applications with pagination - Page: {}, Size: {}", page, size);

        // Create a pageable object with the requested page and size
        Pageable pageable = PageRequest.of(page, size);

        // Retrieve job applications using the pageable object
        Page<JobApplication> jobApplications = jobApplicationService.getAllJobApplicationsPage(pageable);
        // Check if the list is empty
        if (jobApplications.isEmpty()) {
            log.info("No job applications found");
            return ResponseEntity.noContent().build();
        }
        log.info("Returning {} job applications", jobApplications.getTotalElements());
        return ResponseEntity.ok(jobApplications);
    }

    @GetMapping("/{recruiterId}/feedback")
    public ResponseEntity<Page<Feedback>> getAllFeedbacks(@PathVariable UUID recruiterId,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Recruiter recruiter = recruiterService.findById(recruiterId);
        if (recruiter == null) {
            return ResponseEntity.notFound().build();
        }
        log.info("Received request to get job applications with pagination - Page: {}, Size: {}", page, size);
        //log.info("Received request to get all feedbacks");
        Pageable pageable = PageRequest.of(page, size);
        Page<Feedback> feedbacks = feedbackService.getAllFeedbacksPage(pageable);

        if (feedbacks.isEmpty()) {
            log.info("No feedbacks found");
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} feedbacks", feedbacks.getTotalElements());
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/{recruiterId}/interviews")
    public ResponseEntity<Page<InterviewSchedule>> getAllInterviewSchedules(@PathVariable UUID recruiterId,
                                                                            @RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "10") int size) {
        Recruiter recruiter = recruiterService.findById(recruiterId);
        if (recruiter == null) {
            return ResponseEntity.notFound().build();
        }
        log.info("Received request to get job applications with pagination - Page: {}, Size: {}", page, size);
        //log.info("Received request to get all feedbacks");
        Pageable pageable = PageRequest.of(page, size);
        Page<InterviewSchedule> interviewSchedules = interviewScheduleService.getAllInterviewSchedulesPage(pageable);

        if (interviewSchedules.isEmpty()) {
            log.info("No interview schedules found");
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} interview schedules", interviewSchedules.getTotalElements());
        return ResponseEntity.ok(interviewSchedules);
    }

    @PostMapping("/{recruiterId}/interviews")
    public ResponseEntity<InterviewSchedule> createInterviewSchedule(
        @PathVariable UUID recruiterId,
        @Validated @RequestBody InterviewSchedule interviewSchedule) {

    log.info("Received request to create interview schedule for student ID: {}, recruiter ID: {}, and job ID: {}",
            interviewSchedule.getStudent().getStudentId(),
            interviewSchedule.getRecruiter().getRecruiterId(),
            interviewSchedule.getJobPosting().getJobId());

    // Ensure the recruiterId in the InterviewSchedule matches the PathVariable
    if (!recruiterId.equals(interviewSchedule.getRecruiter().getRecruiterId())) {
        log.error("Mismatch between PathVariable recruiterId: {} and InterviewSchedule recruiterId: {}",
                recruiterId, interviewSchedule.getRecruiter().getRecruiterId());
        return ResponseEntity.badRequest().build();
    }

    // Find the recruiter by ID
    Recruiter recruiter = recruiterService.findById(recruiterId);
    if (recruiter == null) {
        log.error("Recruiter with ID {} not found", recruiterId);
        return ResponseEntity.notFound().build();
    }

    // Ensure that studentId and jobId in the InterviewSchedule are valid
    if (interviewSchedule.getStudent() == null || interviewSchedule.getStudent().getStudentId() == null) {
        log.error("Student information is missing or invalid in the InterviewSchedule");
        return ResponseEntity.badRequest().build();
    }

    if (interviewSchedule.getJobPosting() == null || interviewSchedule.getJobPosting().getJobId() == null) {
        log.error("Job Posting information is missing or invalid in the InterviewSchedule");
        return ResponseEntity.badRequest().build();
    }

    log.info("Creating interview schedule for student ID: {}, recruiter ID: {}, and job ID: {}",
            interviewSchedule.getStudent().getStudentId(),
            interviewSchedule.getRecruiter().getRecruiterId(),
            interviewSchedule.getJobPosting().getJobId());

    // Associate the recruiter with the interview schedule
    interviewSchedule.setRecruiter(recruiter);

    // Create the interview schedule
    InterviewSchedule createdInterviewSchedule = interviewScheduleService.createInterviewSchedule(interviewSchedule);

    if (createdInterviewSchedule == null) {
        log.error("Interview schedule creation failed for recruiter ID: {}", recruiterId);
        return ResponseEntity.badRequest().build();
    }

    log.info("Interview schedule created successfully with ID: {}", createdInterviewSchedule.getScheduleId());
    return new ResponseEntity<>(HttpStatus.CREATED);
}

@PutMapping("/{recruiterId}/interviews/{scheduleId}")
public ResponseEntity<InterviewSchedule> updateInterviewSchedule(
        @PathVariable UUID recruiterId,
        @PathVariable UUID scheduleId,
        @Validated @RequestBody InterviewSchedule interviewScheduleDetails) {

    log.info("Received request to update interview schedule with ID: {} for recruiter ID: {}", scheduleId, recruiterId);

    // Ensure the recruiterId in the InterviewSchedule matches the PathVariable
    if (!recruiterId.equals(interviewScheduleDetails.getRecruiter().getRecruiterId())) {
        log.error("Mismatch between PathVariable recruiterId: {} and InterviewSchedule recruiterId: {}",
                recruiterId, interviewScheduleDetails.getRecruiter().getRecruiterId());
        return ResponseEntity.badRequest().build();
    }

    // Find the recruiter by ID
    Recruiter recruiter = recruiterService.findById(recruiterId);
    if (recruiter == null) {
        log.error("Recruiter with ID {} not found", recruiterId);
        return ResponseEntity.notFound().build();
    }

    // Ensure that studentId and jobId in the InterviewSchedule are valid
    if (interviewScheduleDetails.getStudent() == null || interviewScheduleDetails.getStudent().getStudentId() == null) {
        log.error("Student information is missing or invalid in the InterviewSchedule");
        return ResponseEntity.badRequest().build();
    }

    if (interviewScheduleDetails.getJobPosting() == null || interviewScheduleDetails.getJobPosting().getJobId() == null) {
        log.error("Job Posting information is missing or invalid in the InterviewSchedule");
        return ResponseEntity.badRequest().build();
    }

    // Associate the recruiter with the interview schedule
    interviewScheduleDetails.setRecruiter(recruiter);

    log.info("Updating interview schedule with ID: {} for student ID: {}, recruiter ID: {}, and job ID: {}",
            scheduleId,
            interviewScheduleDetails.getStudent().getStudentId(),
            interviewScheduleDetails.getRecruiter().getRecruiterId(),
            interviewScheduleDetails.getJobPosting().getJobId());

    // Update the interview schedule
    InterviewSchedule updatedInterviewSchedule = interviewScheduleService.updateInterviewSchedule(scheduleId, interviewScheduleDetails);

    if (updatedInterviewSchedule == null) {
        log.info("Interview schedule update failed. No interview schedule found with ID: {}", scheduleId);
        return ResponseEntity.notFound().build();
    }

    log.info("Interview schedule updated successfully with ID: {}", updatedInterviewSchedule.getScheduleId());
    return ResponseEntity.ok(updatedInterviewSchedule);
}

//pending
    @PostMapping("/{recruiterId}/feedback")
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
    @PutMapping("/{recruiterId}/applications/{applicationId}+")
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


    /*
@PutMapping("/{recruiterId}/applications/{applicationId}")
public ResponseEntity<JobApplication> updateJobApplication(
        @PathVariable UUID recruiterId,
        @PathVariable UUID applicationId,
        @Validated @RequestBody JobApplication jobApplicationDetails) {

    // Ensure the recruiterId in the JobApplication matches the PathVariable
    if (!recruiterId.equals(jobApplicationDetails.getJobPosting().getRecruiter().getRecruiterId())) {
        log.error("Mismatch between PathVariable recruiterId: {} and JobApplication recruiterId: {}",
                recruiterId, jobApplicationDetails.getJobPosting().getRecruiter().getRecruiterId());
        return ResponseEntity.badRequest().build();
    }

    // Find the recruiter by ID
    Recruiter recruiter = recruiterService.findById(recruiterId);
    if (recruiter == null) {
        log.error("Recruiter with ID {} not found", recruiterId);
        return ResponseEntity.notFound().build();
    }

    // Log the received request
    log.info("Received request to update job application with ID: {} for recruiter ID: {}",
            applicationId, recruiterId);

    // Check if the JobPosting in the JobApplication details is valid
    if (jobApplicationDetails.getJobPosting() == null || jobApplicationDetails.getJobPosting().getJobId() == null) {
        log.error("Job Posting information is missing or invalid in the JobApplication");
        return ResponseEntity.badRequest().build();
    }

    // Proceed with updating the job application
    JobApplication updatedJobApplication = jobApplicationService.updateJobApplication(applicationId, jobApplicationDetails);

    // Check if the update operation was successful
    if (updatedJobApplication == null) {
        log.info("Job application update failed. No job application found with ID: {}", applicationId);
        return ResponseEntity.notFound().build();
    }

    // Log the success and return the updated job application
    log.info("Job application updated successfully with ID: {}", updatedJobApplication.getApplicationId());
    return ResponseEntity.ok(updatedJobApplication);
}


@PutMapping("/{studentId}/applications/{applicationId}")
public ResponseEntity<JobApplication> updateJobApplication(
        @PathVariable UUID studentId,
        @PathVariable UUID applicationId,
        @Validated @RequestBody JobApplication jobApplicationDetails) {

    // Ensure the studentId in the JobApplication matches the PathVariable
    if (!studentId.equals(jobApplicationDetails.getStudent().getStudentId())) {
        log.error("Mismatch between PathVariable studentId: {} and JobApplication studentId: {}",
                studentId, jobApplicationDetails.getStudent().getStudentId());
        return ResponseEntity.badRequest().build();
    }

    // Find the student by ID
    Student student = studentService.findById(studentId);
    if (student == null) {
        log.error("Student with ID {} not found", studentId);
        return ResponseEntity.notFound().build();
    }

    // Log the received request
    log.info("Received request to update job application with ID: {} for student ID: {}", applicationId, studentId);

    // Check if the JobPosting in the JobApplication details is valid
    if (jobApplicationDetails.getJobPosting() == null || jobApplicationDetails.getJobPosting().getJobId() == null) {
        log.error("Job Posting information is missing or invalid in the JobApplication");
        return ResponseEntity.badRequest().build();
    }

    // Proceed with updating the job application
    JobApplication updatedJobApplication = jobApplicationService.updateJobApplication(applicationId, jobApplicationDetails);

    // Check if the update operation was successful
    if (updatedJobApplication == null) {
        log.info("Job application update failed. No job application found with ID: {}", applicationId);
        return ResponseEntity.notFound().build();
    }

    // Log the success and return the updated job application
    log.info("Job application updated successfully with ID: {}", updatedJobApplication.getApplicationId());
    return ResponseEntity.ok(updatedJobApplication);
}



  */



}
