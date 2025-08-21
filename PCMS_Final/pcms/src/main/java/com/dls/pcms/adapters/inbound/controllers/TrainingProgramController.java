package com.dls.pcms.adapters.inbound.controllers;

import com.dls.pcms.application.services.StudentService;
import com.dls.pcms.domain.models.Student;
import com.dls.pcms.domain.models.TrainingProgram;
import com.dls.pcms.application.services.TrainingProgramService;
import jakarta.persistence.EntityNotFoundException;
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
@RequestMapping("/api/training-programs")
@Slf4j
public class TrainingProgramController {

    @Autowired
    private TrainingProgramService trainingProgramService;
    @Autowired
    private StudentService studentService;
    @PostMapping
    public ResponseEntity<TrainingProgram> createTrainingProgram(@Validated @RequestBody TrainingProgram trainingProgram) {
        log.info("Received request to create training program with title: {}", trainingProgram.getTitle());

        TrainingProgram createdTrainingProgram = trainingProgramService.createTrainingProgram(trainingProgram);

        if (createdTrainingProgram == null) {
            log.error("Training program creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        log.info("Training program created successfully with ID: {}", createdTrainingProgram.getProgramId());
        return new ResponseEntity<>(createdTrainingProgram, HttpStatus.CREATED);
    }

    @GetMapping("/{trainingProgramId}")
    public ResponseEntity<TrainingProgram> getTrainingProgramById(@PathVariable UUID trainingProgramId) {
        log.info("Received request to get training program by ID: {}", trainingProgramId);

        Optional<TrainingProgram> trainingProgram = trainingProgramService.getTrainingProgramById(trainingProgramId);

        if (trainingProgram.isEmpty()) {
            log.info("No training program found with ID: {}", trainingProgramId);
            return ResponseEntity.notFound().build();
        }

        log.info("Returning training program with ID: {}", trainingProgramId);
        return ResponseEntity.ok(trainingProgram.get());
    }

    @GetMapping
    public ResponseEntity<List<TrainingProgram>> getAllTrainingPrograms() {
        log.info("Received request to get all training programs");

        List<TrainingProgram> trainingPrograms = trainingProgramService.getAllTrainingPrograms();

        if (trainingPrograms.isEmpty()) {
            log.info("No training programs found");
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} training programs", trainingPrograms.size());
        return ResponseEntity.ok(trainingPrograms);
    }

    @PutMapping("/{trainingProgramId}")
    public ResponseEntity<TrainingProgram> updateTrainingProgram(
            @PathVariable UUID trainingProgramId,
            @Validated @RequestBody TrainingProgram trainingProgramDetails) {
        log.info("Received request to update training program with ID: {}", trainingProgramId);

        TrainingProgram updatedTrainingProgram = trainingProgramService.updateTrainingProgram(trainingProgramId, trainingProgramDetails);

        if (updatedTrainingProgram == null) {
            log.info("Training program update failed. No training program found with ID: {}", trainingProgramId);
            return ResponseEntity.notFound().build();
        }

        log.info("Training program updated successfully with ID: {}", trainingProgramId);
        return ResponseEntity.ok(updatedTrainingProgram);
    }

    @DeleteMapping("/{trainingProgramId}")
    public ResponseEntity<Void> deleteTrainingProgram(@PathVariable UUID trainingProgramId) {
        log.info("Received request to delete training program with ID: {}", trainingProgramId);

        trainingProgramService.deleteTrainingProgram(trainingProgramId);

        log.info("Training program deleted successfully with ID: {}", trainingProgramId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<TrainingProgram>> getTrainingProgramsByTitle(@PathVariable String title) {
        log.info("Received request to get training programs by title: {}", title);

        List<TrainingProgram> trainingPrograms = trainingProgramService.getTrainingProgramsByTitle(title);

        if (trainingPrograms.isEmpty()) {
            log.info("No training programs found for title: {}", title);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} training programs for title: {}", trainingPrograms.size(), title);
        return ResponseEntity.ok(trainingPrograms);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<TrainingProgram>> getTrainingProgramsByDateRange(
            @RequestParam("startDate") @Validated java.time.LocalDate startDate,
            @RequestParam("endDate") @Validated java.time.LocalDate endDate) {
        log.info("Received request to get training programs between {} and {}", startDate, endDate);

        List<TrainingProgram> trainingPrograms = trainingProgramService.getTrainingProgramsByDateRange(startDate, endDate);

        if (trainingPrograms.isEmpty()) {
            log.info("No training programs found between {} and {}", startDate, endDate);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} training programs between {} and {}", trainingPrograms.size(), startDate, endDate);
        return ResponseEntity.ok(trainingPrograms);
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



}
