package com.dls.pcms.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private UUID userId;
    private String username;
    private String hashedPassword;
    private String salt;
    private String email;
    private UUID roleId; // Assuming roleId is sufficient for DTO purposes
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
