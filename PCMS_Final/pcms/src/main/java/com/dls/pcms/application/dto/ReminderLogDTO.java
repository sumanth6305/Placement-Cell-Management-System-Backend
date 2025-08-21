package com.dls.pcms.application.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReminderLogDTO {

    private UUID reminderLogId;
    private UUID studentId;
    private int reminderNumber;
    private LocalDateTime reminderSentAt;
    private LocalDateTime acknowledgedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
