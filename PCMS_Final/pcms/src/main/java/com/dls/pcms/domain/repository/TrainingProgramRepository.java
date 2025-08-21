package com.dls.pcms.domain.repository;

import com.dls.pcms.domain.models.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, UUID> {

    List<TrainingProgram> findByTitle(String title);

    List<TrainingProgram> findByDescriptionContaining(String keyword);

    List<TrainingProgram> findByStartDateBefore(java.time.LocalDate date);

    List<TrainingProgram> findByEndDateAfter(java.time.LocalDate date);

    List<TrainingProgram> findByStartDateBetween(java.time.LocalDate startDate, java.time.LocalDate endDate);

    // Add custom methods as needed, similar to the RecruiterRepository

    default List<TrainingProgram> findByTitleOrDescription(@Param("title") String title, @Param("description") String description) {
        // Custom logic to find training programs by title or description
        List<TrainingProgram> trainingProgramList = null;
        // Implement your custom logic here
        return trainingProgramList;
    }
}
