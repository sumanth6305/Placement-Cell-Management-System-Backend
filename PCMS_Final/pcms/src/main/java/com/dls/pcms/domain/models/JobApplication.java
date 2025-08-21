package com.dls.pcms.domain.models;

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
@Table(name = "JobApplication")
public class JobApplication {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "application_id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID applicationId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private JobPosting jobPosting;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "applied_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime appliedAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (this.applicationId == null) {
            this.applicationId = UUID.randomUUID();
        }
    }
}
