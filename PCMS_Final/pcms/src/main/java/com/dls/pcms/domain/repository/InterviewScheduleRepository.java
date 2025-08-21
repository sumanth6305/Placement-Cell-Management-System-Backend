package com.dls.pcms.domain.repository;

import com.dls.pcms.domain.models.InterviewSchedule;
import com.dls.pcms.domain.models.JobPosting;
import com.dls.pcms.domain.models.Recruiter;
import com.dls.pcms.domain.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InterviewScheduleRepository extends JpaRepository<InterviewSchedule, UUID> {

    // Custom queries based on related entities
    List<InterviewSchedule> findByStudent(Student studentId);

    List<InterviewSchedule> findByRecruiter(Recruiter recruiter);

    List<InterviewSchedule> findByJobPosting(JobPosting job);
}
