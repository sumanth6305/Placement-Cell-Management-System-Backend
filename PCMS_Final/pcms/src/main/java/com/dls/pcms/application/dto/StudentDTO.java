
package com.dls.pcms.application.dto;

import com.dls.pcms.domain.models.JobPosting;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDTO {

    private UUID studentId;
    private String name;
    private LocalDate dateOfBirth;
    private String gender;
    private String email;
    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String academicDetails;
    private String linkedinProfile;
    private String githubProfile;
    private String personalWebsite;
    private String skills;
    private String projects;
    private String certifications;
    private String languages;
    private String professionalExperience;
    private String internships;
    private String awardsAndHonors;
    private String interests;
    private String studentReferences;
    private String additionalInformation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


