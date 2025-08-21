package com.dls.pcms.application.dto;

import com.dls.pcms.domain.models.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditLogDTO {

    private UUID logId;
    private User userId;
    private String action;
    private LocalDateTime timestamp;
}
