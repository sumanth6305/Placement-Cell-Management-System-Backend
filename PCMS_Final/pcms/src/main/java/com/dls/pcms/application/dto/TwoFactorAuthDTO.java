package com.dls.pcms.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwoFactorAuthDTO {

    private UUID authId;
    private UUID userId;
    private String methodType;
    private boolean isEnabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
