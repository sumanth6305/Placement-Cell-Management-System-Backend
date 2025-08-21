package com.dls.pcms.infrastructure.repositories;

import com.dls.pcms.application.services.JobPostingService;
import com.dls.pcms.domain.models.JobPosting;
import com.dls.pcms.domain.models.Recruiter;
import com.dls.pcms.domain.models.Student;
import com.dls.pcms.domain.repository.JobPostingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class JobPostingServiceImpl implements JobPostingService {

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Override
    public JobPosting createJobPosting(JobPosting jobPosting) {
        log.info("Creating new job posting with title: {}", jobPosting.getJobTitle());
        JobPosting savedJobPosting = jobPostingRepository.save(jobPosting);
        log.info("Job posting created successfully with ID: {}", savedJobPosting.getJobId());
        return savedJobPosting;
    }

    @Override
    public Optional<JobPosting> getJobPostingById(UUID jobId) {
        log.info("Fetching job posting by ID: {}", jobId);
        Optional<JobPosting> jobPosting = jobPostingRepository.findById(jobId);
        if (jobPosting.isPresent()) {
            log.info("Job posting found with ID: {}", jobId);
        } else {
            log.info("No job posting found with ID: {}", jobId);
        }
        return jobPosting;
    }

    @Override
    public List<JobPosting> getAllJobPostings() {
        log.info("Fetching all job postings");
        List<JobPosting> jobPostings = jobPostingRepository.findAll();
        log.info("Found {} job postings", jobPostings.size());
        return jobPostings;
    }

    @Override
    public JobPosting updateJobPosting(UUID jobId, JobPosting jobPostingDetails) {
        log.info("Updating job posting with ID: {}", jobId);
        Optional<JobPosting> existingJobPostingOpt = jobPostingRepository.findById(jobId);

        if (existingJobPostingOpt.isPresent()) {
            JobPosting existingJobPosting = existingJobPostingOpt.get();
            existingJobPosting.setJobTitle(jobPostingDetails.getJobTitle());
            existingJobPosting.setJobDescription(jobPostingDetails.getJobDescription());
            existingJobPosting.setEligibilityCriteria(jobPostingDetails.getEligibilityCriteria());
            existingJobPosting.setUpdatedAt(jobPostingDetails.getUpdatedAt());

            JobPosting updatedJobPosting = jobPostingRepository.save(existingJobPosting);
            log.info("Job posting updated successfully with ID: {}", jobId);
            return updatedJobPosting;
        } else {
            log.error("No job posting found with ID: {}", jobId);
            return null;
        }
    }

    @Override
    public void deleteJobPosting(UUID jobId) {
        log.info("Deleting job posting with ID: {}", jobId);
        jobPostingRepository.deleteById(jobId);
        log.info("Job posting deleted successfully with ID: {}", jobId);
    }

    @Override
    public List<JobPosting> getJobPostingsByRecruiterId(Recruiter recruiterId) {
        log.info("Fetching job postings by recruiter ID: {}", recruiterId);
        List<JobPosting> jobPostings = jobPostingRepository.findByRecruiterId(recruiterId);
        log.info("Found {} job postings for recruiter ID: {}", jobPostings.size(), recruiterId);
        return jobPostings;
    }


    @Override
    public Page<JobPosting> getAllJobPostingspage(Pageable pageable) {
        log.info("Fetching job postings with pagination - Page: {}, Size: {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<JobPosting> jobPostings = jobPostingRepository.findAll(pageable);
        log.info("Found {} job postings", jobPostings.getTotalElements());
        return jobPostings;
    }


    public JobPosting findById(UUID jobId) {
        Optional<JobPosting> jobPosting = jobPostingRepository.findById(jobId);
        return jobPosting.orElse(null);
    }


}
