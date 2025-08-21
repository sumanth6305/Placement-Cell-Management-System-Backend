package com.dls.pcms.application.services;

import com.dls.pcms.domain.models.InterviewSchedule;
import com.dls.pcms.domain.models.Student;
import com.dls.pcms.domain.models.Recruiter;
import com.dls.pcms.domain.models.JobPosting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InterviewScheduleService {

    InterviewSchedule createInterviewSchedule(InterviewSchedule interviewSchedule);

    Optional<InterviewSchedule> getInterviewScheduleById(UUID scheduleId);

    List<InterviewSchedule> getAllInterviewSchedules();

    InterviewSchedule updateInterviewSchedule(UUID scheduleId, InterviewSchedule interviewScheduleDetails);

    void deleteInterviewSchedule(UUID scheduleId);

    List<InterviewSchedule> getInterviewSchedulesByStudentId(Student studentId);

    List<InterviewSchedule> getInterviewSchedulesByRecruiterId(Recruiter recruiterId);

    List<InterviewSchedule> getInterviewSchedulesByJobPostingId(JobPosting jobId);

    Page<InterviewSchedule> getAllInterviewSchedulesPage(Pageable pageable);

    // Additional methods for working with DTOs if needed

    // Add more custom methods as needed
}
