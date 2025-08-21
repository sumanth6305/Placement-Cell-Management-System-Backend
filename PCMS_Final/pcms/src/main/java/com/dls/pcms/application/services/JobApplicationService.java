package com.dls.pcms.application.services;

import com.dls.pcms.domain.models.Feedback;
import com.dls.pcms.domain.models.JobApplication;
import com.dls.pcms.domain.models.Student;
import com.dls.pcms.domain.models.JobPosting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobApplicationService {

    JobApplication createJobApplication(JobApplication jobApplication);

    Optional<JobApplication> getJobApplicationById(UUID applicationId);

    List<JobApplication> getAllJobApplications();

    JobApplication updateJobApplication(UUID applicationId, JobApplication jobApplicationDetails);

    void deleteJobApplication(UUID applicationId);

    List<JobApplication> getJobApplicationsByStudentId(Student student);

    List<JobApplication> getJobApplicationsByJobPostingId(JobPosting jobPosting);
    Page<JobApplication> getAllJobApplicationsPage(Pageable pageable);

    // Add more custom methods as needed
}
