package com.dls.pcms.application.dto;
import com.dls.pcms.domain.models.Recruiter;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class JobPostingDTO {

    private UUID jobId;
    private Recruiter recruiterId;
    private String jobTitle;
    private String jobDescription;
    private String eligibilityCriteria;
    private LocalDateTime postedAt;
    private LocalDateTime updatedAt;
}
