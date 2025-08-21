package com.dls.pcms.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Recruiter")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Recruiter {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "recruiter_id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID recruiterId;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "company_profile")
    private String companyProfile;

    @Column(name = "company_website")
    private String companyWebsite;

    @Column(name = "company_portal_link")
    private String companyPortalLink;

    @Column(name = "wikipedia_link")
    private String wikipediaLink;

    @Column(name = "contact_email", nullable = false)
    private String contactEmail;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (this.recruiterId == null) {
            this.recruiterId = UUID.randomUUID();
        }
    }
}


