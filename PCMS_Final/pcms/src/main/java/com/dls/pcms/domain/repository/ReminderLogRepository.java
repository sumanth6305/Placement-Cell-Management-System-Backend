package com.dls.pcms.domain.repository;

import com.dls.pcms.domain.models.ReminderLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReminderLogRepository extends JpaRepository<ReminderLog, UUID> {

    List<ReminderLog> findByStudentId(UUID studentId);

    List<ReminderLog> findByReminderNumber(int reminderNumber);

    List<ReminderLog> findBySentAtBetween(LocalDateTime start, LocalDateTime end);

    List<ReminderLog> findByAcknowledgedAtBetween(LocalDateTime start, LocalDateTime end);

    // Add custom methods as needed, similar to the StudentRepository

    default List<ReminderLog> findByStudentIdOrReminderNumber(UUID studentId, int reminderNumber) {
        // Custom logic to find reminder logs by studentId or reminderNumber
        List<ReminderLog> reminderLogList = null;
        // Implement your custom logic here
        return reminderLogList;
    }
}
