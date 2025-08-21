package com.dls.pcms.application.dto;

import com.dls.pcms.domain.models.Student;
import com.dls.pcms.domain.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDTO {

    private UUID feedbackId;
    private User user;
    private String type;
    private String comments;
    private Integer rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
