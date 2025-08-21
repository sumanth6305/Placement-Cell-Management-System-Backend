package com.dls.pcms.domain.repository;

import com.dls.pcms.domain.models.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {

    List<AuditLog> findByUserUserId(UUID userId);

    List<AuditLog> findByActionContaining(String action);

    List<AuditLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

    // Add custom methods as needed

    default List<AuditLog> findByUserAndAction(UUID userId, String action) {
        // Custom logic to find audit logs by user ID and action
        List<AuditLog> auditLogList = null;
        // Implement your custom logic here
        return auditLogList;
    }
}
