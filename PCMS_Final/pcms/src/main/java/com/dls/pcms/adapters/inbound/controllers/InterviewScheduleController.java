package com.dls.pcms.adapters.inbound.controllers;

import com.dls.pcms.domain.models.InterviewSchedule;
import com.dls.pcms.application.services.InterviewScheduleService;
import com.dls.pcms.domain.models.Student;
import com.dls.pcms.domain.models.Recruiter;
import com.dls.pcms.domain.models.JobPosting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/interview-schedules")
@Slf4j
public class InterviewScheduleController {

    @Autowired
    private InterviewScheduleService interviewScheduleService;

    @PostMapping
    public ResponseEntity<InterviewSchedule> createInterviewSchedule(@Validated @RequestBody InterviewSchedule interviewSchedule) {
       log.info("Received request to create interview schedule for student ID: {}, recruiter ID: {}, and job ID: {}",
                interviewSchedule.getStudent().getStudentId(),
                interviewSchedule.getRecruiter().getRecruiterId(),
                interviewSchedule.getJobPosting().getJobId());

        InterviewSchedule createdInterviewSchedule = interviewScheduleService.createInterviewSchedule(interviewSchedule);

        if (createdInterviewSchedule == null) {
            log.error("Interview schedule creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }
        else {

            log.info("Interview schedule created successfully with ID: {}", createdInterviewSchedule.getScheduleId());
            return new ResponseEntity<>(HttpStatus.CREATED);



        }
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<InterviewSchedule> getInterviewScheduleById(@PathVariable UUID scheduleId) {
        log.info("Received request to get interview schedule by ID: {}", scheduleId);

        Optional<InterviewSchedule> interviewSchedule = interviewScheduleService.getInterviewScheduleById(scheduleId);

        if (interviewSchedule.isEmpty()) {
            log.info("No interview schedule found with ID: {}", scheduleId);
            return ResponseEntity.notFound().build();
        }

        log.info("Returning interview schedule with ID: {}", scheduleId);
        return ResponseEntity.ok(interviewSchedule.get());
    }

    @GetMapping
    public ResponseEntity<List<InterviewSchedule>> getAllInterviewSchedules() {
        log.info("Received request to get all interview schedules");

        List<InterviewSchedule> interviewSchedules = interviewScheduleService.getAllInterviewSchedules();

        if (interviewSchedules.isEmpty()) {
            log.info("No interview schedules found");
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} interview schedules", interviewSchedules.size());
        return ResponseEntity.ok(interviewSchedules);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<InterviewSchedule> updateInterviewSchedule(
            @PathVariable UUID scheduleId,
            @Validated @RequestBody InterviewSchedule interviewScheduleDetails) {
        log.info("Received request to update interview schedule with ID: {}", scheduleId);

        InterviewSchedule updatedInterviewSchedule = interviewScheduleService.updateInterviewSchedule(scheduleId, interviewScheduleDetails);

        if (updatedInterviewSchedule == null) {
            log.info("Interview schedule update failed. No interview schedule found with ID: {}", scheduleId);
            return ResponseEntity.notFound().build();
        }

        log.info("Interview schedule updated successfully with ID: {}", scheduleId);
        return ResponseEntity.ok(updatedInterviewSchedule);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteInterviewSchedule(@PathVariable UUID scheduleId) {
        log.info("Received request to delete interview schedule with ID: {}", scheduleId);

        interviewScheduleService.deleteInterviewSchedule(scheduleId);

        log.info("Interview schedule deleted successfully with ID: {}", scheduleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<InterviewSchedule>> getInterviewSchedulesByStudentId(@PathVariable Student studentId) {
        log.info("Received request to get interview schedules by student ID: {}", studentId);

        List<InterviewSchedule> interviewSchedules = interviewScheduleService.getInterviewSchedulesByStudentId(studentId);

        if (interviewSchedules.isEmpty()) {
            log.info("No interview schedules found for student ID: {}", studentId);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} interview schedules for student ID: {}", interviewSchedules.size(), studentId);
        return ResponseEntity.ok(interviewSchedules);
    }

    @GetMapping("/recruiter/{recruiterId}")
    public ResponseEntity<List<InterviewSchedule>> getInterviewSchedulesByRecruiterId(@PathVariable Recruiter recruiterId) {
        log.info("Received request to get interview schedules by recruiter ID: {}", recruiterId);

        List<InterviewSchedule> interviewSchedules = interviewScheduleService.getInterviewSchedulesByRecruiterId(recruiterId);

        if (interviewSchedules.isEmpty()) {
            log.info("No interview schedules found for recruiter ID: {}", recruiterId);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} interview schedules for recruiter ID: {}", interviewSchedules.size(), recruiterId);
        return ResponseEntity.ok(interviewSchedules);
    }

    @GetMapping("/job-posting/{jobId}")
    public ResponseEntity<List<InterviewSchedule>> getInterviewSchedulesByJobPostingId(@PathVariable JobPosting jobId) {
        log.info("Received request to get interview schedules by job posting ID: {}", jobId);

        List<InterviewSchedule> interviewSchedules = interviewScheduleService.getInterviewSchedulesByJobPostingId(jobId);

        if (interviewSchedules.isEmpty()) {
            log.info("No interview schedules found for job posting ID: {}", jobId);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} interview schedules for job posting ID: {}", interviewSchedules.size(), jobId);
        return ResponseEntity.ok(interviewSchedules);
    }
}
