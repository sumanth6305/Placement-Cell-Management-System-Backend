package com.dls.pcms.infrastructure.repositories;

import com.dls.pcms.application.services.JobApplicationService;
import com.dls.pcms.domain.models.JobApplication;
import com.dls.pcms.domain.models.Student;
import com.dls.pcms.domain.models.JobPosting;
import com.dls.pcms.domain.repository.JobApplicationRepository;
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
public class JobApplicationServiceImpl implements JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Override
    public JobApplication createJobApplication(JobApplication jobApplication) {
        log.info("Creating new job application for student ID: {}", jobApplication.getStudent().getStudentId());
        JobApplication savedJobApplication = jobApplicationRepository.save(jobApplication);
        log.info("Job application created successfully with ID: {} and saves data", savedJobApplication.getApplicationId());
        return savedJobApplication;
    }

    @Override
    public Optional<JobApplication> getJobApplicationById(UUID applicationId) {
        log.info("Fetching job application by ID: {}", applicationId);
        Optional<JobApplication> jobApplication = jobApplicationRepository.findById(applicationId);
        if (jobApplication.isPresent()) {
            log.info("Job application found with ID: {}", applicationId);
        } else {
            log.info("No job application found with ID: {}", applicationId);
        }
        return jobApplication;
    }

    @Override
    public List<JobApplication> getAllJobApplications() {
        log.info("Fetching all job applications");
        List<JobApplication> jobApplications = jobApplicationRepository.findAll();
        log.info("Found {} job applications", jobApplications.size());
        return jobApplications;
    }

    @Override
    public JobApplication updateJobApplication(UUID applicationId, JobApplication jobApplicationDetails) {
        log.info("Updating job application with ID: {}", applicationId);
        Optional<JobApplication> existingJobApplicationOpt = jobApplicationRepository.findById(applicationId);

        if (existingJobApplicationOpt.isPresent()) {
            JobApplication existingJobApplication = existingJobApplicationOpt.get();
            existingJobApplication.setStatus(jobApplicationDetails.getStatus());
            existingJobApplication.setUpdatedAt(jobApplicationDetails.getUpdatedAt());

            JobApplication updatedJobApplication = jobApplicationRepository.save(existingJobApplication);
            log.info("Job application updated successfully with ID: {}", applicationId);
            return updatedJobApplication;
        } else {
            log.error("No job application found with ID: {}", applicationId);
            return null;
        }
    }

    @Override
    public void deleteJobApplication(UUID applicationId) {
        log.info("Deleting job application with ID: {}", applicationId);
        jobApplicationRepository.deleteById(applicationId);
        log.info("Job application deleted successfully with ID: {}", applicationId);
    }

    @Override
    public List<JobApplication> getJobApplicationsByStudentId(Student student) {
        log.info("Fetching job applications by student ID: {}", student);
        List<JobApplication> jobApplications = jobApplicationRepository.findByStudent(student);
        log.info("Found {} job applications for student ID: {}", jobApplications.size(), student);
        return jobApplications;
    }

    @Override
    public List<JobApplication> getJobApplicationsByJobPostingId(JobPosting jobPosting) {
        log.info("Fetching job applications by job posting ID: {}", jobPosting);
        List<JobApplication> jobApplications = jobApplicationRepository.findByJobPosting(jobPosting);
        log.info("Found {} job applications for job posting ID: {}", jobApplications.size(), jobPosting);
        return jobApplications;
    }
    @Override
    public Page<JobApplication> getAllJobApplicationsPage(Pageable pageable){
        Page<JobApplication> jobApplications = jobApplicationRepository.findAll(pageable);
        return jobApplications;
    }



}
