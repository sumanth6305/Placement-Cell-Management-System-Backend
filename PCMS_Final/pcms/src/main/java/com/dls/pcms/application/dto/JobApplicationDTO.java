package com.dls.pcms.application.dto;

import com.dls.pcms.domain.models.JobPosting;
import com.dls.pcms.domain.models.Student;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobApplicationDTO {


    private UUID applicationId;
    private Student student;
    private JobPosting job;
    private String status;
    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;
}
