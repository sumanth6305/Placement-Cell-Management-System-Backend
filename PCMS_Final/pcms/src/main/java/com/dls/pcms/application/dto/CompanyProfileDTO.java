package com.dls.pcms.application.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyProfileDTO {

    private UUID companyId;
    private String companyName;
    private String industryType;
    private String hrContactName;
    private String hrContactEmail;
    private String hrContactPhone;
    private String location;
    private String eligibleBranches;
    private String jobOpportunities;
    private String internshipOpportunities;
    private String offerType;
    private double packageOffered;
    private LocalDate registrationDeadline;
    private String additionalInformation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
