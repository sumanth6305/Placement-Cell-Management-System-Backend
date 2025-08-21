package com.dls.pcms.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@Table(name = "JobPosting")
public class JobPosting {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "job_id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID jobId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "recruiter_id", referencedColumnName = "recruiter_id")
    private Recruiter recruiterId;

    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    @Column(name = "job_description")
    private String jobDescription;

    @Column(columnDefinition = "TEXT")
    private String eligibilityCriteria; // Stored as JSON string

    @JsonProperty("eligibilityCriteria")
    public JsonNode getEligibilityCriteriaAsJsonNode() {
        try {
            return new ObjectMapper().readTree(this.eligibilityCriteria);
        } catch (IOException e) {
            return null;
        }
    }

    @JsonProperty("eligibilityCriteria")
    public void setEligibilityCriteriaAsJsonNode(JsonNode jsonNode) {
        this.eligibilityCriteria = jsonNode.toString();
    }

    @Column(name = "posted_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime postedAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();


    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        this.postedAt = LocalDateTime.now();
    }

    public Recruiter getRecruiter() {
        return recruiterId;
    }

    public void setRecruiter(Recruiter recruiterId) {
        this.recruiterId = recruiterId;
    }
}
