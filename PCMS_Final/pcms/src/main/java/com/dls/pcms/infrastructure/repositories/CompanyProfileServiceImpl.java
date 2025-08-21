package com.dls.pcms.infrastructure.repositories;

import com.dls.pcms.application.services.CompanyProfileService;
import com.dls.pcms.domain.models.CompanyProfile;
import com.dls.pcms.domain.repository.CompanyProfileRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@Slf4j
public class CompanyProfileServiceImpl implements CompanyProfileService {

    @Autowired
    private CompanyProfileRepository companyProfileRepository;

    @Override
    public CompanyProfile createCompanyProfile(CompanyProfile companyProfile) {
        log.info("Creating new company profile with name: {}", companyProfile.getCompanyName());
        CompanyProfile savedCompanyProfile = companyProfileRepository.save(companyProfile);
        log.info("Company profile created successfully with ID: {}", savedCompanyProfile.getCompanyId());
        return savedCompanyProfile;
    }

    @Override
    public Optional<CompanyProfile> getCompanyProfileById(UUID companyId) {
        log.info("Fetching company profile by ID: {}", companyId);
        Optional<CompanyProfile> companyProfile = companyProfileRepository.findById(companyId);

        if (companyProfile.isPresent()) {
            log.info("Company profile found with ID: {}", companyId);
        } else {
            log.info("No company profile found with ID: {}", companyId);
        }

        return companyProfile;
    }

    @Override
    public List<CompanyProfile> getAllCompanyProfiles() {
        log.info("Fetching all company profiles");
        List<CompanyProfile> companyProfiles = companyProfileRepository.findAll();
        log.info("Found {} company profiles", companyProfiles.size());
        return companyProfiles;
    }

    @Override
    public CompanyProfile updateCompanyProfile(UUID companyId, CompanyProfile companyProfileDetails) {
        log.info("Updating company profile with ID: {}", companyId);
        Optional<CompanyProfile> existingCompanyOpt = companyProfileRepository.findById(companyId);

        if (existingCompanyOpt.isPresent()) {
            CompanyProfile existingCompany = existingCompanyOpt.get();

            existingCompany.setCompanyName(companyProfileDetails.getCompanyName());
            existingCompany.setIndustryType(companyProfileDetails.getIndustryType());
            existingCompany.setCompanyWebsite(companyProfileDetails.getCompanyWebsite());
            existingCompany.setHrContactName(companyProfileDetails.getHrContactName());
            existingCompany.setHrContactEmail(companyProfileDetails.getHrContactEmail());
            existingCompany.setHrContactPhone(companyProfileDetails.getHrContactPhone());
            existingCompany.setLocation(companyProfileDetails.getLocation());
            existingCompany.setJobOpportunities(companyProfileDetails.getJobOpportunities());
            existingCompany.setRecruitmentDate(companyProfileDetails.getRecruitmentDate());
            existingCompany.setEligibleBranches(companyProfileDetails.getEligibleBranches());
            existingCompany.setMinimumCGPA(companyProfileDetails.getMinimumCGPA());
            existingCompany.setInternshipOpportunities(companyProfileDetails.getInternshipOpportunities());
            existingCompany.setSelectionProcess(companyProfileDetails.getSelectionProcess());
            existingCompany.setOfferType(companyProfileDetails.getOfferType());
            existingCompany.setPackageOffered(companyProfileDetails.getPackageOffered());
            existingCompany.setWorkLocation(companyProfileDetails.getWorkLocation());
            existingCompany.setBondDetails(companyProfileDetails.getBondDetails());
            existingCompany.setLastUpdated(companyProfileDetails.getLastUpdated());

            CompanyProfile updatedCompany = companyProfileRepository.save(existingCompany);
            log.info("Company profile updated successfully with ID: {}", companyId);
            return updatedCompany;
        } else {
            log.error("No company profile found with ID: {}", companyId);
            throw new EntityNotFoundException("Company profile with ID " + companyId + " not found");
        }
    }

    @Override
    public void deleteCompanyProfile(UUID companyId) {
        log.info("Deleting company profile with ID: {}", companyId);
        companyProfileRepository.deleteById(companyId);
        log.info("Company profile deleted successfully with ID: {}", companyId);
    }

    @Override
    public Page<CompanyProfile> getCompanyProfilesByIndustryType(String industryType, Pageable pageable) {
        log.info("Fetching company profiles by industry type: {}", industryType);
        Page<CompanyProfile> companyProfiles = companyProfileRepository.findByIndustryType(industryType, pageable);
        log.info("Found {} company profiles for industry type: {}", companyProfiles.getTotalElements(), industryType);
        return companyProfiles;
    }
}
