package com.dls.pcms.adapters.inbound.controllers;

import com.dls.pcms.domain.models.CompanyProfile;
import com.dls.pcms.application.services.CompanyProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/company-profiles")
@Slf4j
public class CompanyProfileController {

    @Autowired
    private CompanyProfileService companyProfileService;

    // Create a new CompanyProfile
    @PostMapping
    public ResponseEntity<CompanyProfile> createCompanyProfile(@RequestBody CompanyProfile companyProfile) {
        log.info("Creating new company profile for company: {}", companyProfile.getCompanyName());
        CompanyProfile createdCompanyProfile = companyProfileService.createCompanyProfile(companyProfile);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Get a CompanyProfile by ID
    @GetMapping("/{companyId}")
    public ResponseEntity<Optional<CompanyProfile>> getCompanyProfileById(@PathVariable UUID companyId) {
        log.info("Fetching company profile with ID: {}", companyId);
        Optional<CompanyProfile> companyProfile = companyProfileService.getCompanyProfileById(companyId);

        return ResponseEntity.ok(companyProfile);
    }

    // Get all CompanyProfiles
    @GetMapping
    public ResponseEntity<List<CompanyProfile>> getAllCompanyProfiles() {
        log.info("Fetching all company profiles");
        List<CompanyProfile> companyProfiles = companyProfileService.getAllCompanyProfiles();
        return ResponseEntity.ok(companyProfiles);
    }

    // Update a CompanyProfile by ID
    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyProfile> updateCompanyProfile(
            @PathVariable UUID companyId,
            @RequestBody CompanyProfile companyProfile) {
        log.info("Updating company profile with ID: {}", companyId);
        CompanyProfile updatedCompanyProfile = companyProfileService.updateCompanyProfile(companyId, companyProfile);
        return ResponseEntity.ok(updatedCompanyProfile);
    }

    // Delete a CompanyProfile by ID
    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteCompanyProfile(@PathVariable UUID companyId) {
        log.info("Deleting company profile with ID: {}", companyId);
        companyProfileService.deleteCompanyProfile(companyId);
        return ResponseEntity.noContent().build();
    }
}
