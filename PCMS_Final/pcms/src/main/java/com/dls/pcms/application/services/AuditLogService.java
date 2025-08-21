package com.dls.pcms.application.services;

import com.dls.pcms.domain.models.AuditLog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuditLogService {

    AuditLog createAuditLog(AuditLog auditLog);

    Optional<AuditLog> getAuditLogById(UUID logId);

    List<AuditLog> getAllAuditLogs();

    List<AuditLog> getAuditLogsByUserId(UUID userId);

    List<AuditLog> getAuditLogsByAction(String action);

    List<AuditLog> getAuditLogsByTimestampRange(LocalDateTime start, LocalDateTime end);

    void deleteAuditLog(UUID logId);

    // Add more custom methods as needed
}
