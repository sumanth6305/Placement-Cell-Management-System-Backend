package com.dls.pcms.domain.repository;

import com.dls.pcms.domain.models.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, UUID> {

    List<Recruiter> findByCompanyName(String companyName);

    List<Recruiter> findByContactEmail(String contactEmail);

    List<Recruiter> findByCompanyWebsite(String companyWebsite);

    List<Recruiter> findByCompanyPortalLink(String companyPortalLink);

    List<Recruiter> findByWikipediaLink(String wikipediaLink);

    List<Recruiter> findByCompanyProfileContaining(String keyword);

    // Add custom methods as needed, similar to the InventoryRepository

    default List<Recruiter> findByEmailOrCompany(@Param("email") String email, @Param("company") String company) {
        // Custom logic to find recruiters by email or company name
        List<Recruiter> recruiterList = null;
        // Implement your custom logic here
        return recruiterList;
    }
}
