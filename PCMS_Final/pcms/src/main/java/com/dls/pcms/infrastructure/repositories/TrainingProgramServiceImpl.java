package com.dls.pcms.infrastructure.repositories;

import com.dls.pcms.application.services.TrainingProgramService;
import com.dls.pcms.domain.models.TrainingProgram;
import com.dls.pcms.domain.repository.TrainingProgramRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class TrainingProgramServiceImpl implements TrainingProgramService {

    @Autowired
    private TrainingProgramRepository trainingProgramRepository;

    @Override
    public TrainingProgram createTrainingProgram(TrainingProgram trainingProgram) {
        log.info("Creating new training program with title: {}", trainingProgram.getTitle());
        TrainingProgram savedTrainingProgram = trainingProgramRepository.save(trainingProgram);
        log.info("Training program created successfully with ID: {}", savedTrainingProgram.getProgramId());
        return savedTrainingProgram;
    }

    @Override
    public Optional<TrainingProgram> getTrainingProgramById(UUID programId) {
        log.info("Fetching training program by ID: {}", programId);
        Optional<TrainingProgram> trainingProgram = trainingProgramRepository.findById(programId);
        if (trainingProgram.isPresent()) {
            log.info("Training program found with ID: {}", programId);
        } else {
            log.info("No training program found with ID: {}", programId);
        }
        return trainingProgram;
    }

    @Override
    public List<TrainingProgram> getAllTrainingPrograms() {
        log.info("Fetching all training programs");
        List<TrainingProgram> trainingPrograms = trainingProgramRepository.findAll();
        log.info("Found {} training programs", trainingPrograms.size());
        return trainingPrograms;
    }

    @Override
    public TrainingProgram updateTrainingProgram(UUID programId, TrainingProgram trainingProgramDetails) {
        log.info("Updating training program with ID: {}", programId);
        Optional<TrainingProgram> existingTrainingProgramOpt = trainingProgramRepository.findById(programId);

        if (existingTrainingProgramOpt.isPresent()) {
            TrainingProgram existingTrainingProgram = existingTrainingProgramOpt.get();
            existingTrainingProgram.setTitle(trainingProgramDetails.getTitle());
            existingTrainingProgram.setDescription(trainingProgramDetails.getDescription());
            existingTrainingProgram.setStartDate(trainingProgramDetails.getStartDate());
            existingTrainingProgram.setEndDate(trainingProgramDetails.getEndDate());
            existingTrainingProgram.setUpdatedAt(trainingProgramDetails.getUpdatedAt());

            TrainingProgram updatedTrainingProgram = trainingProgramRepository.save(existingTrainingProgram);
            log.info("Training program updated successfully with ID: {}", programId);
            return updatedTrainingProgram;
        } else {
            log.error("No training program found with ID: {}", programId);
            return null;
        }
    }

    @Override
    public void deleteTrainingProgram(UUID programId) {
        log.info("Deleting training program with ID: {}", programId);
        trainingProgramRepository.deleteById(programId);
        log.info("Training program deleted successfully with ID: {}", programId);
    }

    @Override
    public List<TrainingProgram> getTrainingProgramsByTitle(String title) {
        log.info("Fetching training programs by title: {}", title);
        List<TrainingProgram> trainingPrograms = trainingProgramRepository.findByTitle(title);
        log.info("Found {} training programs for title: {}", trainingPrograms.size(), title);
        return trainingPrograms;
    }
    @Override
    public List<TrainingProgram> getTrainingProgramsByDateRange(LocalDate startDate, LocalDate endDate) {
        // Fetching all training programs within the date range
        List<TrainingProgram> trainingPrograms = trainingProgramRepository.findByStartDateBetween(startDate, endDate);
        return trainingPrograms;
    }

    //getTrainingProgramsByDateRange(LocalDate startDate, LocalDate endDate);
    // Add more custom methods as needed
}
