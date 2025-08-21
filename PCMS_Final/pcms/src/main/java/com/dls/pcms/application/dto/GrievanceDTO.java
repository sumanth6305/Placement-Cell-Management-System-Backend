package com.dls.pcms.application.dto;

import com.dls.pcms.domain.models.GrievanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrievanceDTO {

    private UUID grievanceId;
    private UUID studentId;
    private String description;
    private String subject;
    private GrievanceStatus status;
    private String resumeFilename;
    private byte[] resumeFile;
    private LocalDateTime uploadedAt;
    private LocalDateTime updatedAt;

    public GrievanceDTO(UUID grievanceId, UUID studentId, String subject, String description, GrievanceStatus status, String resumeFilename) {
        this.grievanceId=grievanceId;
        this.studentId=studentId;
        this.subject=subject;
        this.description=description;
        this.status=status;
        this.resumeFilename=resumeFilename;
    }


/*
    public enum GrievanceStatus {
        SUBMITTED,
        UNDER_REVIEW,
        RESOLVED
    }

 */
}
