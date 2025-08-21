package com.dls.pcms.application.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecruiterDTO {

    private UUID recruiterId;
    private String companyName;
    private String companyProfile;
    private String companyWebsite;
    private String companyPortalLink;
    private String wikipediaLink;
    private String contactEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
