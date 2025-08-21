package com.dls.pcms.application.services;

import com.dls.pcms.domain.models.JobPosting;
import com.dls.pcms.domain.models.Recruiter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobPostingService {

    JobPosting createJobPosting(JobPosting jobPosting);

    Optional<JobPosting> getJobPostingById(UUID jobId);

    List<JobPosting> getAllJobPostings();

    JobPosting updateJobPosting(UUID jobId, JobPosting jobPostingDetails);

    void deleteJobPosting(UUID jobId);

  List<JobPosting> getJobPostingsByRecruiterId(Recruiter recruiterId);
    Page<JobPosting> getAllJobPostingspage(Pageable pageable);

    JobPosting findById(UUID jobId);

    // Add more custom methods as needed
}
