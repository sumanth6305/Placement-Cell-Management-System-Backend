package com.dls.pcms.application.services;

import com.dls.pcms.domain.models.Recruiter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecruiterService {

    Recruiter createRecruiter(Recruiter recruiter);

    Optional<Recruiter> getRecruiterById(UUID recruiterId);

    List<Recruiter> getAllRecruiters();

    Recruiter updateRecruiter(UUID recruiterId, Recruiter recruiterDetails);

    void deleteRecruiter(UUID recruiterId);

    List<Recruiter> getRecruitersByCompanyName(String companyName);

    List<Recruiter> getRecruitersByContactEmail(String contactEmail);

    Recruiter findById(UUID recruiterId);

    // Add more custom methods as needed
}
