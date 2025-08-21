package com.dls.pcms.domain.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ReminderLog")
public class ReminderLog {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "reminder_log_id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID reminderLogId;

    @Column(name = "student_id", nullable = false)
    private UUID studentId;

    @Column(name = "reminder_number", nullable = false)
    private int reminderNumber;

    @Column(name = "reminder_sent_at", nullable = false)
    @Builder.Default
    private LocalDateTime reminderSentAt = LocalDateTime.now();

    @Column(name = "acknowledged_at")
    private LocalDateTime acknowledgedAt = LocalDateTime.now();

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PrePersist
    public void prePersist() {
        if (this.reminderLogId == null) {
            this.reminderLogId = UUID.randomUUID();
        }
        if (this.reminderSentAt == null) {
            this.reminderSentAt = LocalDateTime.now();
        }
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

  @Getter
    @Setter
    private LocalDateTime sentAt;
    @Setter
    @Getter
    private String notes;



}


