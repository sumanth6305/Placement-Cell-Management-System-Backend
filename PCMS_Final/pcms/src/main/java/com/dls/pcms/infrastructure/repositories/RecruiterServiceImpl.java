package com.dls.pcms.infrastructure.repositories;

import com.dls.pcms.application.services.RecruiterService;
import com.dls.pcms.domain.models.Recruiter;
import com.dls.pcms.domain.models.Student;
import com.dls.pcms.domain.repository.RecruiterRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class RecruiterServiceImpl implements RecruiterService {

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Override
    public Recruiter createRecruiter(Recruiter recruiter) {
        log.info("Creating new recruiter with company name: {}", recruiter.getCompanyName());
        Recruiter savedRecruiter = recruiterRepository.save(recruiter);
        log.info("Recruiter created successfully with ID: {}", savedRecruiter.getRecruiterId());
        return savedRecruiter;
    }

    @Override
    public Optional<Recruiter> getRecruiterById(UUID recruiterId) {
        log.info("Fetching recruiter by ID: {}", recruiterId);
        Optional<Recruiter> recruiter = recruiterRepository.findById(recruiterId);
        if (recruiter.isPresent()) {
            log.info("Recruiter found with ID: {}", recruiterId);
        } else {
            log.info("No recruiter found with ID: {}", recruiterId);
        }
        return recruiter;
    }

    @Override
    public List<Recruiter> getAllRecruiters() {
        log.info("Fetching all recruiters");
        List<Recruiter> recruiters = recruiterRepository.findAll();
        log.info("Found {} recruiters", recruiters.size());
        return recruiters;
    }

    @Override
    public Recruiter updateRecruiter(UUID recruiterId, Recruiter recruiterDetails) {
        log.info("Updating recruiter with ID: {}", recruiterId);
        Optional<Recruiter> existingRecruiterOpt = recruiterRepository.findById(recruiterId);

        if (existingRecruiterOpt.isPresent()) {
            Recruiter existingRecruiter = existingRecruiterOpt.get();
            existingRecruiter.setCompanyName(recruiterDetails.getCompanyName());
            existingRecruiter.setCompanyProfile(recruiterDetails.getCompanyProfile());
            existingRecruiter.setCompanyWebsite(recruiterDetails.getCompanyWebsite());
            existingRecruiter.setCompanyPortalLink(recruiterDetails.getCompanyPortalLink());
            existingRecruiter.setWikipediaLink(recruiterDetails.getWikipediaLink());
            existingRecruiter.setContactEmail(recruiterDetails.getContactEmail());
            existingRecruiter.setUpdatedAt(recruiterDetails.getUpdatedAt());

            Recruiter updatedRecruiter = recruiterRepository.save(existingRecruiter);
            log.info("Recruiter updated successfully with ID: {}", recruiterId);
            return updatedRecruiter;
        } else {
            log.error("No recruiter found with ID: {}", recruiterId);
            return null;
        }
    }

    @Override
    public void deleteRecruiter(UUID recruiterId) {
        log.info("Deleting recruiter with ID: {}", recruiterId);
        recruiterRepository.deleteById(recruiterId);
        log.info("Recruiter deleted successfully with ID: {}", recruiterId);
    }

    @Override
    public List<Recruiter> getRecruitersByCompanyName(String companyName) {
        log.info("Fetching recruiters by company name: {}", companyName);
        List<Recruiter> recruiters = recruiterRepository.findByCompanyName(companyName);
        log.info("Found {} recruiters for company name: {}", recruiters.size(), companyName);
        return recruiters;
    }

    @Override
    public List<Recruiter> getRecruitersByContactEmail(String contactEmail) {
        log.info("Fetching recruiters by contact email: {}", contactEmail);
        List<Recruiter> recruiters = recruiterRepository.findByContactEmail(contactEmail);
        log.info("Found {} recruiters for contact email: {}", recruiters.size(), contactEmail);
        return recruiters;
    }

    public Recruiter findById(UUID recruiterId) {
        Optional<Recruiter> recruiter = recruiterRepository.findById(recruiterId);
        return recruiter.orElse(null);
    }

}
