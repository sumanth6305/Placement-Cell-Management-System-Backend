package com.dls.pcms.application.services;

import com.dls.pcms.domain.models.CompanyProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompanyProfileService {

    CompanyProfile createCompanyProfile(CompanyProfile companyProfile);

    Optional<CompanyProfile> getCompanyProfileById(UUID companyId);

    List<CompanyProfile> getAllCompanyProfiles();

    CompanyProfile updateCompanyProfile(UUID companyId, CompanyProfile companyProfileDetails);

    void deleteCompanyProfile(UUID companyId);

    Page<CompanyProfile> getCompanyProfilesByIndustryType(String industryType, Pageable pageable);

    // Add more custom methods as needed
}
