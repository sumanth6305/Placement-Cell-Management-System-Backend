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
@Table(name = "InterviewSchedule")
public class InterviewSchedule {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "schedule_id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID scheduleId;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "student_id",referencedColumnName = "student_id")
    private Student student;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "recruiter_id", referencedColumnName = "recruiter_id")
    private Recruiter recruiter;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "job_id",referencedColumnName = "job_id")
    private JobPosting jobPosting;

    @Column(name = "interview_date", nullable = false)
    private LocalDateTime interviewDate;

    @Column(name = "status", length = 50)
    private String status;

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
        if (this.scheduleId == null) {
            this.scheduleId = UUID.randomUUID();
        }
    }
}
