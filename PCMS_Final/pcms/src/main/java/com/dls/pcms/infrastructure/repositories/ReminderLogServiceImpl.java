package com.dls.pcms.infrastructure.repositories;

import com.dls.pcms.application.services.ReminderLogService;
import com.dls.pcms.domain.models.ReminderLog;
import com.dls.pcms.domain.repository.ReminderLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ReminderLogServiceImpl implements ReminderLogService {

    @Autowired
    private ReminderLogRepository reminderLogRepository;

    @Override
    public ReminderLog createReminderLog(ReminderLog reminderLog) {
        log.info("Creating new reminder log for student with ID: {}", reminderLog.getStudentId());
        ReminderLog savedReminderLog = reminderLogRepository.save(reminderLog);
        log.info("Reminder log created successfully with ID: {}", savedReminderLog.getReminderLogId());
        return savedReminderLog;
    }

    @Override
    public Optional<ReminderLog> getReminderLogById(UUID reminderLogId) {
        log.info("Fetching reminder log by ID: {}", reminderLogId);
        Optional<ReminderLog> reminderLog = reminderLogRepository.findById(reminderLogId);
        if (reminderLog.isPresent()) {
            log.info("Reminder log found with ID: {}", reminderLogId);
        } else {
            log.info("No reminder log found with ID: {}", reminderLogId);
        }
        return reminderLog;
    }

    @Override
    public List<ReminderLog> getAllReminderLogs() {
        log.info("Fetching all reminder logs");
        List<ReminderLog> reminderLogs = reminderLogRepository.findAll();
        log.info("Found {} reminder logs", reminderLogs.size());
        return reminderLogs;
    }

    @Override
    public ReminderLog updateReminderLog(UUID reminderLogId, ReminderLog reminderLogDetails) {
        log.info("Updating reminder log with ID: {}", reminderLogId);
        Optional<ReminderLog> existingReminderLogOpt = reminderLogRepository.findById(reminderLogId);

        if (existingReminderLogOpt.isPresent()) {
            ReminderLog existingReminderLog = existingReminderLogOpt.get();
            existingReminderLog.setReminderNumber(reminderLogDetails.getReminderNumber());
            existingReminderLog.setSentAt(reminderLogDetails.getSentAt());
            existingReminderLog.setAcknowledgedAt(reminderLogDetails.getAcknowledgedAt());
             existingReminderLog.setNotes(reminderLogDetails.getNotes());
            existingReminderLog.setUpdatedAt(reminderLogDetails.getUpdatedAt());

            ReminderLog updatedReminderLog = reminderLogRepository.save(existingReminderLog);
            log.info("Reminder log updated successfully with ID: {}", reminderLogId);
            return updatedReminderLog;
        } else {
            log.error("No reminder log found with ID: {}", reminderLogId);
            return null;
        }
    }

    @Override
    public void deleteReminderLog(UUID reminderLogId) {
        log.info("Deleting reminder log with ID: {}", reminderLogId);
        reminderLogRepository.deleteById(reminderLogId);
        log.info("Reminder log deleted successfully with ID: {}", reminderLogId);
    }

    @Override
    public List<ReminderLog> getReminderLogsByStudentId(UUID studentId) {
        log.info("Fetching reminder logs for student with ID: {}", studentId);
        List<ReminderLog> reminderLogs = reminderLogRepository.findByStudentId(studentId);
        log.info("Found {} reminder logs for student with ID: {}", reminderLogs.size(), studentId);
        return reminderLogs;
    }

    @Override
    public List<ReminderLog> getReminderLogsByReminderNumber(int reminderNumber) {
        log.info("Fetching reminder logs with reminder number: {}", reminderNumber);
        List<ReminderLog> reminderLogs = reminderLogRepository.findByReminderNumber(reminderNumber);
        log.info("Found {} reminder logs with reminder number: {}", reminderLogs.size(), reminderNumber);
        return reminderLogs;
    }

    @Override
    public List<ReminderLog> getReminderLogsSentBetween(LocalDateTime start, LocalDateTime end) {
        log.info("Fetching reminder logs sent between {} and {}", start, end);
        List<ReminderLog> reminderLogs = reminderLogRepository.findBySentAtBetween(start, end);
        log.info("Found {} reminder logs sent between {} and {}", reminderLogs.size(), start, end);
        return reminderLogs;
    }

    @Override
    public List<ReminderLog> getAcknowledgedReminderLogsBetween(LocalDateTime start, LocalDateTime end) {
        log.info("Fetching acknowledged reminder logs between {} and {}", start, end);
        List<ReminderLog> reminderLogs = reminderLogRepository.findByAcknowledgedAtBetween(start, end);
        log.info("Found {} acknowledged reminder logs between {} and {}", reminderLogs.size(), start, end);
        return reminderLogs;
    }
}
