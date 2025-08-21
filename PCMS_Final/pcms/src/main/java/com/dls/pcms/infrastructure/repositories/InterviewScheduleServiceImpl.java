package com.dls.pcms.infrastructure.repositories;

import com.dls.pcms.application.services.InterviewScheduleService;
import com.dls.pcms.domain.models.InterviewSchedule;
import com.dls.pcms.domain.models.Student;
import com.dls.pcms.domain.models.Recruiter;
import com.dls.pcms.domain.models.JobPosting;
import com.dls.pcms.domain.repository.InterviewScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class InterviewScheduleServiceImpl implements InterviewScheduleService {

    @Autowired
    private InterviewScheduleRepository interviewScheduleRepository;

    @Override
    public InterviewSchedule createInterviewSchedule(InterviewSchedule interviewSchedule) {
        log.info("Creating new interview schedule for student ID: {}", interviewSchedule.getStudent().getStudentId());
        InterviewSchedule savedInterviewSchedule = interviewScheduleRepository.save(interviewSchedule);
        log.info("Interview schedule created successfully with ID: {}", savedInterviewSchedule.getScheduleId());
        return savedInterviewSchedule;
    }

    @Override
    public Optional<InterviewSchedule> getInterviewScheduleById(UUID scheduleId) {
        log.info("Fetching interview schedule by ID: {}", scheduleId);
        Optional<InterviewSchedule> interviewSchedule = interviewScheduleRepository.findById(scheduleId);
        if (interviewSchedule.isPresent()) {
            log.info("Interview schedule found with ID: {}", scheduleId);
        } else {
            log.info("No interview schedule found with ID: {}", scheduleId);
        }
        return interviewSchedule;
    }

    @Override
    public List<InterviewSchedule> getAllInterviewSchedules() {
        log.info("Fetching all interview schedules");
        List<InterviewSchedule> interviewSchedules = interviewScheduleRepository.findAll();
        log.info("Found {} interview schedules", interviewSchedules.size());
        return interviewSchedules;
    }

    @Override
    public InterviewSchedule updateInterviewSchedule(UUID scheduleId, InterviewSchedule interviewScheduleDetails) {
        log.info("Updating interview schedule with ID: {}", scheduleId);
        Optional<InterviewSchedule> existingInterviewScheduleOpt = interviewScheduleRepository.findById(scheduleId);

        if (existingInterviewScheduleOpt.isPresent()) {
            InterviewSchedule existingInterviewSchedule = existingInterviewScheduleOpt.get();
            existingInterviewSchedule.setInterviewDate(interviewScheduleDetails.getInterviewDate());
            existingInterviewSchedule.setStatus(interviewScheduleDetails.getStatus());
            existingInterviewSchedule.setUpdatedAt(interviewScheduleDetails.getUpdatedAt());

            InterviewSchedule updatedInterviewSchedule = interviewScheduleRepository.save(existingInterviewSchedule);
            log.info("Interview schedule updated successfully with ID: {}", scheduleId);
            return updatedInterviewSchedule;
        } else {
            log.error("No interview schedule found with ID: {}", scheduleId);
            return null;
        }
    }

    @Override
    public void deleteInterviewSchedule(UUID scheduleId) {
        log.info("Deleting interview schedule with ID: {}", scheduleId);
        interviewScheduleRepository.deleteById(scheduleId);
        log.info("Interview schedule deleted successfully with ID: {}", scheduleId);
    }

    @Override
    public List<InterviewSchedule> getInterviewSchedulesByStudentId(Student student) {
        log.info("Fetching interview schedules by student: {}", student);
        List<InterviewSchedule> interviewSchedules = interviewScheduleRepository.findByStudent(student);
        log.info("Found {} interview schedules for student: {}", interviewSchedules.size(), student);
        return interviewSchedules;
    }



    @Override
    public List<InterviewSchedule> getInterviewSchedulesByRecruiterId(Recruiter recruiter) {
        log.info("Fetching interview schedules by recruiter ID: {}", recruiter);
        List<InterviewSchedule> interviewSchedules = interviewScheduleRepository.findByRecruiter(recruiter);
        log.info("Found {} interview schedules for recruiter ID: {}", interviewSchedules.size(), recruiter);
        return interviewSchedules;
    }

    @Override
    public List<InterviewSchedule> getInterviewSchedulesByJobPostingId(JobPosting job) {
        log.info("Fetching interview schedules by job posting ID: {}", job);
        List<InterviewSchedule> interviewSchedules = interviewScheduleRepository.findByJobPosting(job);
        log.info("Found {} interview schedules for job posting ID: {}", interviewSchedules.size(), job);
        return interviewSchedules;
    }

    @Override
    public Page<InterviewSchedule> getAllInterviewSchedulesPage(Pageable pageable) {
        log.info("Fetching paginated interview schedules");
        Page<InterviewSchedule> interviewSchedules = interviewScheduleRepository.findAll(pageable);
        return interviewSchedules;
    }
}
