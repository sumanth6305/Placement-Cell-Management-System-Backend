package com.dls.pcms.domain.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "resume")
public class Resume {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "resume_id", updatable = false, nullable = false)
    private UUID resumeId;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "resume_filename", nullable = false)
    private String resumeFilename;

    @Lob
    @Column(name = "resume_file", nullable = false)
    private byte[] resumeFile;

    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Resume(String resumeFilename, byte[] resumeFile, Student student) {
        this.resumeFilename = resumeFilename;
        this.resumeFile = resumeFile;
        this.student = student;
    }
}
