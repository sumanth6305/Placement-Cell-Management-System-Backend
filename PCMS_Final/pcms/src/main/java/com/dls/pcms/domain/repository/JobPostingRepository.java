package com.dls.pcms.domain.repository;

import com.dls.pcms.domain.models.JobPosting;
import com.dls.pcms.domain.models.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, UUID> {
   List<JobPosting> findByJobTitle(String jobTitle);

    List<JobPosting> findByJobDescriptionContaining(String keyword);

    List<JobPosting> findByEligibilityCriteriaContaining(String criteria);

   List<JobPosting> findByRecruiterId(Recruiter recruiterId);
  //  List<JobPosting> findByStudent_StudentId(UUID studentId);
    // Add custom methods as needed

    default List<JobPosting> findByTitleOrDescription(@Param("title") String title, @Param("description") String description) {
        // Custom logic to find job postings by title or description
        List<JobPosting> jobPostingList = null;

        // Implement your custom logic here
        return jobPostingList;
    }
}
