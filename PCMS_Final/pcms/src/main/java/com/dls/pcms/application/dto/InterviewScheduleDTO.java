package com.dls.pcms.application.dto;

import com.dls.pcms.domain.models.JobPosting;
import com.dls.pcms.domain.models.Recruiter;
import com.dls.pcms.domain.models.Student;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterviewScheduleDTO {

    private UUID scheduleId;
    private Student studentId;
    private Recruiter recruiterId;
    private JobPosting jobId;
    private LocalDateTime interviewDate;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
