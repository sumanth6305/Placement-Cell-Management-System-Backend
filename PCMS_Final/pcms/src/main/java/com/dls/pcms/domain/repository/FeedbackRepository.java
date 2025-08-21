package com.dls.pcms.domain.repository;

import com.dls.pcms.domain.models.Feedback;
import com.dls.pcms.domain.models.JobApplication;
import com.dls.pcms.domain.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {

    List<Feedback> findByType(String type);

    List<Feedback> findByUserUserId(UUID userId);

    List<Feedback> findByRating(Integer rating);

    // Add custom methods as needed

    default List<Feedback> findByUserIdAndType(@Param("userId") UUID userId, @Param("type") String type) {
        // Custom logic to find feedback by userId and type
        List<Feedback> feedbackList = null;
        // Implement your custom logic here
        return feedbackList;
    }
}
