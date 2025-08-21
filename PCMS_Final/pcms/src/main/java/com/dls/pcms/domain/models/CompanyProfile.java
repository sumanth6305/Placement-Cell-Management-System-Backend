package com.dls.pcms.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "CompanyProfile")
public class CompanyProfile {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "company_id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID companyId;

    @Column(name = "company_name", nullable = false)
    @NotBlank(message = "Company Name is mandatory")
    private String companyName;

    @Column(name = "industry_type")
    private String industryType;

    @Column(name = "company_website")
    private String companyWebsite;

    @Column(name = "hr_contact_name")
    private String hrContactName;

    @Column(name = "hr_contact_email")
    @Email(message = "HR contact email should be valid")
    private String hrContactEmail;

    @Column(name = "hr_contact_phone")
    @Pattern(regexp = "^$|[0-9]{10,20}", message = "HR contact phone must be valid")
    private String hrContactPhone;

    @Column(name = "location")
    private String location;

    @Column(name = "job_opportunities", columnDefinition = "TEXT")
    private String jobOpportunities;

    @JsonProperty("job_opportunities")
    public JsonNode getJobOpportunitiesAsJsonNode() {
        try {
            return new ObjectMapper().readTree(this.jobOpportunities);
        } catch (IOException e) {
            return null;
        }
    }

    @JsonProperty("job_opportunities")
    public void setJobOpportunitiesAsJsonNode(JsonNode jsonNode) {
        this.jobOpportunities = jsonNode.toString();
    }


    @Column(name = "recruitment_date")
    private LocalDate recruitmentDate;


    @Column(name = "eligible_branches", columnDefinition = "TEXT")
    private String eligibleBranches;

    @JsonProperty("eligible_branches")
    public JsonNode getEligibleBranchesAsJsonNode() {
        try {
            return new ObjectMapper().readTree(this.eligibleBranches);
        } catch (IOException e) {
            return null;
        }
    }


    @JsonProperty("eligible_branches")
    public void setEligibleBranchesAsJsonNode(JsonNode jsonNode) {
        this.eligibleBranches = jsonNode.toString();
    }




    @Column(name = "minimum_cgpa", precision = 3, scale = 2)
    private BigDecimal minimumCGPA;

    @Column(name = "internship_opportunities")
    private Boolean internshipOpportunities;

    @Column(name = "selection_process", columnDefinition = "TEXT")
    private String selectionProcess;

    @JsonProperty("selection_process")
    public JsonNode getSelectionProcessAsJsonNode() {
        try {
            return new ObjectMapper().readTree(this.selectionProcess);
        } catch (IOException e) {
            return null;
        }
    } 

    @JsonProperty("selection_process")
    public void setSelectionProcessAsJsonNode(JsonNode jsonNode) {
        this.selectionProcess = jsonNode.toString();
    }



    @Column(name = "offer_type")
    private String offerType;

    @Column(name = "package_offered", precision = 10, scale = 2)
    private BigDecimal packageOffered;

    @Column(name = "work_location")
    private String workLocation;

    @Column(name = "bond_details")
    private String bondDetails;

    @Column(name = "last_updated", nullable = false)
    @Builder.Default
    private LocalDateTime lastUpdated = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (this.companyId == null) {
            this.companyId = UUID.randomUUID();
        }
    }
}
