package com.dls.pcms.application.services;

import com.dls.pcms.domain.models.TrainingProgram;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrainingProgramService {

    TrainingProgram createTrainingProgram(TrainingProgram trainingProgram);

    Optional<TrainingProgram> getTrainingProgramById(UUID programId);

    List<TrainingProgram> getAllTrainingPrograms();

    TrainingProgram updateTrainingProgram(UUID programId, TrainingProgram trainingProgramDetails);

    void deleteTrainingProgram(UUID programId);

    List<TrainingProgram> getTrainingProgramsByTitle(String title);

    List<TrainingProgram> getTrainingProgramsByDateRange(LocalDate startDate, LocalDate endDate);


    // Add more custom methods as needed
}
