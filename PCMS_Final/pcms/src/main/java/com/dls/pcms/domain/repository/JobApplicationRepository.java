package com.dls.pcms.domain.repository;

import com.dls.pcms.domain.models.JobApplication;
import com.dls.pcms.domain.models.Student;
import com.dls.pcms.domain.models.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID> {

    List<JobApplication> findByStatus(String status);
    List<JobApplication> findByStudent(Student student);

  //  List<JobApplication> findByStudentId(Student student);

    List<JobApplication> findByJobPosting(JobPosting jobPosting);

    // Add custom methods as needed

    default List<JobApplication> findByStatusOrStudentId(String status, Student student) {
        // Custom logic to find job applications by status or student ID
        List<JobApplication> jobApplicationList = null;

        // Implement your custom logic here
        return jobApplicationList;
    }
}
