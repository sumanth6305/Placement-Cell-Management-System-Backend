package com.dls.pcms.infrastructure.repositories;

import com.dls.pcms.application.services.AuditLogService;
import com.dls.pcms.domain.models.AuditLog;
import com.dls.pcms.domain.repository.AuditLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public AuditLog createAuditLog(AuditLog auditLog) {
        log.info("Creating new audit log for user ID: {}", auditLog.getUser().getUserId());
        AuditLog savedAuditLog = auditLogRepository.save(auditLog);
        log.info("Audit log created successfully with ID: {}", savedAuditLog.getLogId());
        return savedAuditLog;
    }

    @Override
    public Optional<AuditLog> getAuditLogById(UUID logId) {
        log.info("Fetching audit log by ID: {}", logId);
        Optional<AuditLog> auditLog = auditLogRepository.findById(logId);
        if (auditLog.isPresent()) {
            log.info("Audit log found with ID: {}", logId);
        } else {
            log.info("No audit log found with ID: {}", logId);
        }
        return auditLog;
    }

    @Override
    public List<AuditLog> getAllAuditLogs() {
        log.info("Fetching all audit logs");
        List<AuditLog> auditLogs = auditLogRepository.findAll();
        log.info("Found {} audit logs", auditLogs.size());
        return auditLogs;
    }

    @Override
    public List<AuditLog> getAuditLogsByUserId(UUID userId) {
        log.info("Fetching audit logs for user ID: {}", userId);
        List<AuditLog> auditLogs = auditLogRepository.findByUserUserId(userId);
        log.info("Found {} audit logs for user ID: {}", auditLogs.size(), userId);
        return auditLogs;
    }

    @Override
    public List<AuditLog> getAuditLogsByAction(String action) {
        log.info("Fetching audit logs with action containing: {}", action);
        List<AuditLog> auditLogs = auditLogRepository.findByActionContaining(action);
        log.info("Found {} audit logs with action containing: {}", auditLogs.size(), action);
        return auditLogs;
    }

    @Override
    public List<AuditLog> getAuditLogsByTimestampRange(LocalDateTime start, LocalDateTime end) {
        log.info("Fetching audit logs between {} and {}", start, end);
        List<AuditLog> auditLogs = auditLogRepository.findByTimestampBetween(start, end);
        log.info("Found {} audit logs between {} and {}", auditLogs.size(), start, end);
        return auditLogs;
    }

    @Override
    public void deleteAuditLog(UUID logId) {
        log.info("Deleting audit log with ID: {}", logId);
        auditLogRepository.deleteById(logId);
        log.info("Audit log deleted successfully with ID: {}", logId);
    }
}
