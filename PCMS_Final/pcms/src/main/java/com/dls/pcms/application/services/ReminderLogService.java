package com.dls.pcms.application.services;

import com.dls.pcms.domain.models.ReminderLog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReminderLogService {

    ReminderLog createReminderLog(ReminderLog reminderLog);

    Optional<ReminderLog> getReminderLogById(UUID reminderLogId);

    //List<ReminderLog> findBySentAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<ReminderLog> getAllReminderLogs();

    ReminderLog updateReminderLog(UUID reminderLogId, ReminderLog reminderLogDetails);

    void deleteReminderLog(UUID reminderLogId);

    List<ReminderLog> getReminderLogsByStudentId(UUID studentId);

    List<ReminderLog> getReminderLogsByReminderNumber(int reminderNumber);

    List<ReminderLog> getReminderLogsSentBetween(LocalDateTime start, LocalDateTime end);

    List<ReminderLog> getAcknowledgedReminderLogsBetween(LocalDateTime start, LocalDateTime end);

    // Add more custom methods as needed
}
