package com.dls.pcms.application.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDTO {

    private UUID eventId;
    private String title;
    private String description;
    private String location;
    private LocalDate eventDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
