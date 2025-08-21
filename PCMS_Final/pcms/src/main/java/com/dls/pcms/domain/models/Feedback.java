package com.dls.pcms.domain.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "feedback_id", updatable = false, nullable = false)
    private UUID feedbackId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Feedback(User user, String type, String comments, Integer rating) {
        this.user = user;
        this.type = type;
        this.comments = comments;
        this.rating = rating;
    }


    @PrePersist
    public void prePersist() {
        if (this.feedbackId == null) {
            this.feedbackId = UUID.randomUUID();
        }
    }
/*
    public Student getStudent() {
        return studentId;
    }

    public void setStudent(Student studentId) {
        this.studentId=studentId;
    }


 */

}
