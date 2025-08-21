package com.dls.pcms.adapters.inbound.controllers;

import com.dls.pcms.domain.models.AuditLog;
import com.dls.pcms.application.services.AuditLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/audit-logs")
@Slf4j
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @PostMapping
    public ResponseEntity<AuditLog> createAuditLog(@Validated @RequestBody AuditLog auditLog) {
        log.info("Received request to create audit log for user ID: {}", auditLog.getUser().getUserId());

        AuditLog createdAuditLog = auditLogService.createAuditLog(auditLog);

        if (createdAuditLog == null) {
            log.error("Audit log creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        log.info("Audit log created successfully with ID: {}", createdAuditLog.getLogId());
        return new ResponseEntity<>(createdAuditLog, HttpStatus.CREATED);
    }

    @GetMapping("/{logId}")
    public ResponseEntity<AuditLog> getAuditLogById(@PathVariable UUID logId) {
        log.info("Received request to get audit log by ID: {}", logId);

        Optional<AuditLog> auditLog = auditLogService.getAuditLogById(logId);

        if (auditLog.isEmpty()) {
            log.info("No audit log found with ID: {}", logId);
            return ResponseEntity.notFound().build();
        }

        log.info("Returning audit log with ID: {}", logId);
        return ResponseEntity.ok(auditLog.get());
    }

    @GetMapping
    public ResponseEntity<List<AuditLog>> getAllAuditLogs() {
        log.info("Received request to get all audit logs");

        List<AuditLog> auditLogs = auditLogService.getAllAuditLogs();

        if (auditLogs.isEmpty()) {
            log.info("No audit logs found");
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} audit logs", auditLogs.size());
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByUserId(@PathVariable UUID userId) {
        log.info("Received request to get audit logs for user ID: {}", userId);

        List<AuditLog> auditLogs = auditLogService.getAuditLogsByUserId(userId);

        if (auditLogs.isEmpty()) {
            log.info("No audit logs found for user ID: {}", userId);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} audit logs for user ID: {}", auditLogs.size(), userId);
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/action/{action}")
    public ResponseEntity<List<AuditLog>> getAuditLogsByAction(@PathVariable String action) {
        log.info("Received request to get audit logs by action containing: {}", action);

        List<AuditLog> auditLogs = auditLogService.getAuditLogsByAction(action);

        if (auditLogs.isEmpty()) {
            log.info("No audit logs found with action containing: {}", action);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} audit logs with action containing: {}", auditLogs.size(), action);
        return ResponseEntity.ok(auditLogs);
    }

    @GetMapping("/timestamp")
    public ResponseEntity<List<AuditLog>> getAuditLogsByTimestampRange(
            @RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        log.info("Received request to get audit logs between {} and {}", start, end);

        List<AuditLog> auditLogs = auditLogService.getAuditLogsByTimestampRange(start, end);

        if (auditLogs.isEmpty()) {
            log.info("No audit logs found between {} and {}", start, end);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} audit logs between {} and {}", auditLogs.size(), start, end);
        return ResponseEntity.ok(auditLogs);
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<Void> deleteAuditLog(@PathVariable UUID logId) {
        log.info("Received request to delete audit log with ID: {}", logId);

        auditLogService.deleteAuditLog(logId);

        log.info("Audit log deleted successfully with ID: {}", logId);
        return ResponseEntity.noContent().build();
    }
}
