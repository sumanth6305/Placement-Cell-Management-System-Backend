package com.dls.pcms.adapters.inbound.controllers;

import com.dls.pcms.application.services.*;
import com.dls.pcms.domain.models.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@RestController
@RequestMapping("/api/students")
@Slf4j
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private JobApplicationService jobApplicationService;
    @Autowired
    private InterviewScheduleService interviewScheduleService;
    @Autowired
    private FeedbackService feedbackService;
    @PostMapping
    public ResponseEntity<Student> createStudent(@Validated @RequestBody Student student) {
        log.info("Received request to create student with name: {}", student.getName());

        Student createdStudent = studentService.createStudent(student);

        if (createdStudent == null) {
            log.error("Student creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        log.info("Student created successfully with ID: {}", createdStudent.getStudentId());
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable UUID studentId) {
        log.info("Received request to get student by ID: {}", studentId);

        Optional<Student> student = studentService.getStudentById(studentId);

        if (student.isEmpty()) {
            log.info("No student found with ID: {}", studentId);
            return ResponseEntity.notFound().build();
        }

        log.info("Returning student with ID: {}", studentId);
        return ResponseEntity.ok(student.get());
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        log.info("Received request to get all students");

        List<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            log.info("No students found");
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} students", students.size());
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable UUID studentId,
            @Validated @RequestBody Student studentDetails) {
        log.info("Received request to update student with ID: {}", studentId);

        Student updatedStudent = studentService.updateStudent(studentId, studentDetails);

        if (updatedStudent == null) {
            log.info("Student update failed. No student found with ID: {}", studentId);
            return ResponseEntity.notFound().build();
        }

        log.info("Student updated successfully with ID: {}", studentId);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable UUID studentId) {
        log.info("Received request to delete student with ID: {}", studentId);

        studentService.deleteStudent(studentId);

        log.info("Student deleted successfully with ID: {}", studentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{studentId}/skills")
    public ResponseEntity<?> getSkillsByStudentId(@PathVariable UUID studentId) {
        log.info("Received request to fetch skills for student ID: {}", studentId);

        Optional<Student> student = studentService.getSkillsById(studentId);

        if (student.isPresent()) {
            // Assuming the Student entity has a method getSkills() which returns a list or set of skills
            if (student.get().getSkills() != null && !student.get().getSkills().isEmpty()) {
                log.info("Returning skills for student with ID: {}", studentId);
                return new ResponseEntity<>(student.get().getSkills(), HttpStatus.OK);
            } else {
                log.info("No skills found for student with ID: {}", studentId);
                return new ResponseEntity<>("No skills found for this student", HttpStatus.NO_CONTENT);
            }
        } else {
            log.error("Student with ID: {} not found", studentId);
            return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{studentId}/skills")
    public ResponseEntity<?> updateSkillsByStudentId(@PathVariable UUID studentId, @RequestBody Student updatedSkills) {
        log.info("Received request to update skills for student ID: {}", studentId);

        try {
            // Update the skills of the student
            Student updatedStudent = studentService.updateSkillsByStudentId(studentId, updatedSkills);

            log.info("Skills updated successfully for student ID: {}", studentId);
            return new ResponseEntity<>(updatedStudent.getSkills(), HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            log.error("Student with ID: {} not found. Update failed.", studentId);
            return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            log.error("An error occurred while updating skills for student ID: {}", studentId, e);
            return new ResponseEntity<>("An error occurred while updating skills", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Autowired
    private JobPostingService jobPostingService;


  @GetMapping("/{studentId}/jobs")
  public ResponseEntity<Page<JobPosting>> getAllJobPostings(
          @PathVariable UUID studentId,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size) {

      Student student = studentService.findById(studentId);
      if (student == null) {
          return ResponseEntity.notFound().build();
      }

      log.info("Received request to get job postings with pagination - Page: {}, Size: {}", page, size);

      Pageable pageable = PageRequest.of(page, size);
      Page<JobPosting> jobPostings = jobPostingService.getAllJobPostingspage(pageable);

      if (jobPostings.isEmpty()) {
          log.info("No job postings found");
          return ResponseEntity.noContent().build();
      }

      log.info("Returning {} job postings", jobPostings.getTotalElements());
      return ResponseEntity.ok(jobPostings);
  }

  @GetMapping("/{studentId}/applications")
  public ResponseEntity<Page<JobApplication>> getAllJobApplications(
          @PathVariable UUID studentId,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size) {
      // Check if the student exists
      Student student = studentService.findById(studentId);
      if (student == null) {
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

    @GetMapping("/{studentId}/feedback")
    public ResponseEntity<Page<Feedback>> getAllFeedbacks(@PathVariable UUID studentId,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Student student = studentService.findById(studentId);
        if (student == null) {
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

    @GetMapping("/{studentId}/interviews")
    public ResponseEntity<Page<InterviewSchedule>> getAllInterviewSchedules(@PathVariable UUID studentId,
                                                                            @RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "10") int size) {
        Student student = studentService.findById(studentId);
        if (student == null) {
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

    @PostMapping("/{studentId}/applications")
    public ResponseEntity<JobApplication> createJobApplication(
            @PathVariable UUID studentId,
            @Validated @RequestBody JobApplication jobApplication) {
        // Ensure the studentId in the JobApplication matches the PathVariable
        if (!studentId.equals(jobApplication.getStudent().getStudentId())) {
            log.error("Mismatch between PathVariable studentId: {} and JobApplication studentId: {}",
                    studentId, jobApplication.getStudent().getStudentId());
            return ResponseEntity.badRequest().build();
        }

        // Find the student by ID
        Student student = studentService.findById(studentId);
        if (student == null) {
            log.error("Student with ID {} not found", studentId);
            return ResponseEntity.notFound().build();
        }

        log.info("Received request to create job application for student ID: {} and job ID: {}",
                studentId, jobApplication.getJobPosting().getJobId());

        // Associate the student with the job application
        jobApplication.setStudent(student);
        JobApplication createdJobApplication = jobApplicationService.createJobApplication(jobApplication);

        if (createdJobApplication == null) {
            log.error("Job application creation failed for student ID: {}", studentId);
            return ResponseEntity.badRequest().build();
        }

        log.info("Job application created successfully with ID: {}", createdJobApplication.getApplicationId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//pending

    @PostMapping("/{studentId}/feedback")
    public ResponseEntity<Feedback> createFeedback(@Validated @RequestBody Feedback feedback) {
        log.info("Received request to create feedback with type: {}", feedback.getType());

        Feedback createdFeedback = feedbackService.createFeedback(feedback);

        if (createdFeedback == null) {
            log.error("Feedback creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        log.info("Feedback created successfully with ID: {}", createdFeedback.getFeedbackId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


/*
    @PostMapping("/{studentId}/feedback")
    public ResponseEntity<Feedback> createFeedback(
            @PathVariable UUID studentId,
            @Validated @RequestBody Feedback feedback) {

        // Ensure the studentId in the Feedback matches the PathVariable
        if (!studentId.equals(feedback.getStudent().getStudentId())) {
            log.error("Mismatch between PathVariable studentId: {} and Feedback studentId: {}",
                    studentId, feedback.getStudent().getStudentId());
            return ResponseEntity.badRequest().build();
        }

        // Find the student by ID
        Student student = studentService.findById(studentId);
        if (student == null) {
            log.error("Student with ID {} not found", studentId);
            return ResponseEntity.notFound().build();
        }

        log.info("Received request to create feedback for student ID: {} with feedback type: {}",
                studentId, feedback.getType());

        // Associate the student with the feedback
        feedback.setStudent(student);
        Feedback createdFeedback = feedbackService.createFeedback(feedback);

        if (createdFeedback == null) {
            log.error("Feedback creation failed for student ID: {}", studentId);
            return ResponseEntity.badRequest().build();
        }

        log.info("Feedback created successfully with ID: {}", createdFeedback.getFeedbackId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


 */
     @PostMapping("/{studentId}/interviews")
     public ResponseEntity<InterviewSchedule> createInterviewSchedule(
             @PathVariable UUID studentId,
             @Validated @RequestBody InterviewSchedule interviewSchedule) {

         // Ensure the studentId in the InterviewSchedule matches the PathVariable
         if (!studentId.equals(interviewSchedule.getStudent().getStudentId())) {
             log.error("Mismatch between PathVariable studentId: {} and InterviewSchedule studentId: {}",
                     studentId, interviewSchedule.getStudent().getStudentId());
             return ResponseEntity.badRequest().build();
         }

         // Find the student by ID
         Student student = studentService.findById(studentId);
         if (student == null) {
             log.error("Student with ID {} not found", studentId);
             return ResponseEntity.notFound().build();
         }

         // Ensure that recruiterId and jobId in the InterviewSchedule are valid
         if (interviewSchedule.getRecruiter() == null || interviewSchedule.getRecruiter().getRecruiterId() == null) {
             log.error("Recruiter information is missing or invalid in the InterviewSchedule");
             return ResponseEntity.badRequest().build();
         }

         if (interviewSchedule.getJobPosting() == null || interviewSchedule.getJobPosting().getJobId() == null) {
             log.error("Job Posting information is missing or invalid in the InterviewSchedule");
             return ResponseEntity.badRequest().build();
         }

         log.info("Received request to create interview schedule for student ID: {}, recruiter ID: {}, and job ID: {}",
                 studentId,
                 interviewSchedule.getRecruiter().getRecruiterId(),
                 interviewSchedule.getJobPosting().getJobId());

         // Associate the student with the interview schedule
         interviewSchedule.setStudent(student);
         InterviewSchedule createdInterviewSchedule = interviewScheduleService.createInterviewSchedule(interviewSchedule);

         if (createdInterviewSchedule == null) {
             log.error("Interview schedule creation failed for student ID: {}", studentId);
             return ResponseEntity.badRequest().build();
         }

         log.info("Interview schedule created successfully with ID: {}", createdInterviewSchedule.getScheduleId());
         return new ResponseEntity<>(HttpStatus.CREATED);
     }

    @GetMapping("/{studentId}/applications/{applicationId}")
    public ResponseEntity<JobApplication> getJobApplicationById(
            @PathVariable UUID studentId,
            @PathVariable UUID applicationId) {
        log.info("Received request to get job application by ID: {} for student ID: {}", applicationId, studentId);

        // Retrieve the job application by ID
        Optional<JobApplication> jobApplicationOpt = jobApplicationService.getJobApplicationById(applicationId);

        if (jobApplicationOpt.isEmpty()) {
            log.info("No job application found with ID: {}", applicationId);
            return ResponseEntity.notFound().build();
        }

        JobApplication jobApplication = jobApplicationOpt.get();

        // Ensure the studentId in the JobApplication matches the PathVariable
        if (!studentId.equals(jobApplication.getStudent().getStudentId())) {
            log.error("Mismatch between PathVariable studentId: {} and JobApplication studentId: {}",
                    studentId, jobApplication.getStudent().getStudentId());
            return ResponseEntity.badRequest().build();
        }

        log.info("Returning job application with ID: {} for student ID: {}", applicationId, studentId);
        return ResponseEntity.ok(jobApplication);
    }

    @GetMapping("/{studentId}/interviews/{scheduleId}")
    public ResponseEntity<InterviewSchedule> getInterviewScheduleById(
            @PathVariable UUID studentId,
            @PathVariable UUID scheduleId) {
        log.info("Received request to get interview schedule by ID: {} for student ID: {}", scheduleId, studentId);

        // Retrieve the interview schedule by ID
        Optional<InterviewSchedule> interviewScheduleOpt = interviewScheduleService.getInterviewScheduleById(scheduleId);

        if (interviewScheduleOpt.isEmpty()) {
            log.info("No interview schedule found with ID: {}", scheduleId);
            return ResponseEntity.notFound().build();
        }

        InterviewSchedule interviewSchedule = interviewScheduleOpt.get();

        // Ensure the studentId in the InterviewSchedule matches the PathVariable
        if (!studentId.equals(interviewSchedule.getStudent().getStudentId())) {
            log.error("Mismatch between PathVariable studentId: {} and InterviewSchedule studentId: {}",
                    studentId, interviewSchedule.getStudent().getStudentId());
            return ResponseEntity.badRequest().build();
        }

        log.info("Returning interview schedule with ID: {} for student ID: {}", scheduleId, studentId);
        return ResponseEntity.ok(interviewSchedule);
    }





}
