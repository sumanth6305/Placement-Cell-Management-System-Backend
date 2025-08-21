package com.dls.pcms.domain.repository;

import com.dls.pcms.domain.models.CompanyProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface CompanyProfileRepository extends JpaRepository<CompanyProfile, UUID> {

    //List<CompanyProfile> findByCompanyName(String companyName);

    //List<CompanyProfile> findByIndustryType(String industryType);

    //List<CompanyProfile> findByHrContactEmail(String hrContactEmail);

    //List<CompanyProfile> findByLocation(String location);

    //List<CompanyProfile> findByEligibleBranchesContaining(String branch);

    //List<CompanyProfile> findByJobOpportunitiesContaining(String jobOpportunity);

   // List<CompanyProfile> findByInternshipOpportunitiesContaining(String internshipOpportunity);

    //List<CompanyProfile> findByOfferType(String offerType);

    // Custom query example using JPQL to find company profiles by package offered
    @Query("SELECT c FROM CompanyProfile c WHERE c.packageOffered >= :minimumPackage")
    List<CompanyProfile> findByPackageOfferedGreaterThanEqual(@Param("minimumPackage") double minimumPackage);

    // Pagination support for fetching company profiles by industry type
    Page<CompanyProfile> findByIndustryType(String industryType, Pageable pageable);
    
    default List<CompanyProfile> findByCompanyNameOrIndustryType(@Param("companyName") String companyName, @Param("industryType") String industryType) {
        // Custom logic to find company profiles by name or industry type
        List<CompanyProfile> companyProfileList = null;
        // Implement custom logic here
        return companyProfileList;
    }
}
