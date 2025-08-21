package com.dls.pcms.adapters.inbound.controllers;

import com.dls.pcms.domain.models.ReminderLog;
import com.dls.pcms.application.services.ReminderLogService;
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
@RequestMapping("/api/reminder-logs")
@Slf4j
public class ReminderLogController {

    @Autowired
    private ReminderLogService reminderLogService;

    @PostMapping
    public ResponseEntity<ReminderLog> createReminderLog(@Validated @RequestBody ReminderLog reminderLog) {
        log.info("Received request to create reminder log for student ID: {}", reminderLog.getStudentId());

        ReminderLog createdReminderLog = reminderLogService.createReminderLog(reminderLog);

        if (createdReminderLog == null) {
            log.error("Reminder log creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        log.info("Reminder log created successfully with ID: {}", createdReminderLog.getReminderLogId());
        return new ResponseEntity<>(createdReminderLog, HttpStatus.CREATED);
    }

    @GetMapping("/{reminderLogId}")
    public ResponseEntity<ReminderLog> getReminderLogById(@PathVariable UUID reminderLogId) {
        log.info("Received request to get reminder log by ID: {}", reminderLogId);

        Optional<ReminderLog> reminderLog = reminderLogService.getReminderLogById(reminderLogId);

        if (reminderLog.isEmpty()) {
            log.info("No reminder log found with ID: {}", reminderLogId);
            return ResponseEntity.notFound().build();
        }

        log.info("Returning reminder log with ID: {}", reminderLogId);
        return ResponseEntity.ok(reminderLog.get());
    }

    @GetMapping
    public ResponseEntity<List<ReminderLog>> getAllReminderLogs() {
        log.info("Received request to get all reminder logs");

        List<ReminderLog> reminderLogs = reminderLogService.getAllReminderLogs();

        if (reminderLogs.isEmpty()) {
            log.info("No reminder logs found");
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} reminder logs", reminderLogs.size());
        return ResponseEntity.ok(reminderLogs);
    }

    @PutMapping("/{reminderLogId}")
    public ResponseEntity<ReminderLog> updateReminderLog(
            @PathVariable UUID reminderLogId,
            @Validated @RequestBody ReminderLog reminderLogDetails) {
        log.info("Received request to update reminder log with ID: {}", reminderLogId);

        ReminderLog updatedReminderLog = reminderLogService.updateReminderLog(reminderLogId, reminderLogDetails);

        if (updatedReminderLog == null) {
            log.info("Reminder log update failed. No reminder log found with ID: {}", reminderLogId);
            return ResponseEntity.notFound().build();
        }

        log.info("Reminder log updated successfully with ID: {}", reminderLogId);
        return ResponseEntity.ok(updatedReminderLog);
    }

    @DeleteMapping("/{reminderLogId}")
    public ResponseEntity<Void> deleteReminderLog(@PathVariable UUID reminderLogId) {
        log.info("Received request to delete reminder log with ID: {}", reminderLogId);

        reminderLogService.deleteReminderLog(reminderLogId);

        log.info("Reminder log deleted successfully with ID: {}", reminderLogId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ReminderLog>> getReminderLogsByStudentId(@PathVariable UUID studentId) {
        log.info("Received request to get reminder logs for student ID: {}", studentId);

        List<ReminderLog> reminderLogs = reminderLogService.getReminderLogsByStudentId(studentId);

        if (reminderLogs.isEmpty()) {
            log.info("No reminder logs found for student ID: {}", studentId);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} reminder logs for student ID: {}", reminderLogs.size(), studentId);
        return ResponseEntity.ok(reminderLogs);
    }

    @GetMapping("/reminder-number/{reminderNumber}")
    public ResponseEntity<List<ReminderLog>> getReminderLogsByReminderNumber(@PathVariable int reminderNumber) {
        log.info("Received request to get reminder logs with reminder number: {}", reminderNumber);

        List<ReminderLog> reminderLogs = reminderLogService.getReminderLogsByReminderNumber(reminderNumber);

        if (reminderLogs.isEmpty()) {
            log.info("No reminder logs found with reminder number: {}", reminderNumber);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} reminder logs with reminder number: {}", reminderLogs.size(), reminderNumber);
        return ResponseEntity.ok(reminderLogs);
    }

    @GetMapping("/sent-between")
    public ResponseEntity<List<ReminderLog>> getReminderLogsSentBetween(
            @RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        log.info("Received request to get reminder logs sent between {} and {}", start, end);

        List<ReminderLog> reminderLogs = reminderLogService.getReminderLogsSentBetween(start, end);

        if (reminderLogs.isEmpty()) {
            log.info("No reminder logs found sent between {} and {}", start, end);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} reminder logs sent between {} and {}", reminderLogs.size(), start, end);
        return ResponseEntity.ok(reminderLogs);
    }

    @GetMapping("/acknowledged-between")
    public ResponseEntity<List<ReminderLog>> getAcknowledgedReminderLogsBetween(
            @RequestParam LocalDateTime start, @RequestParam LocalDateTime end) {
        log.info("Received request to get acknowledged reminder logs between {} and {}", start, end);

        List<ReminderLog> reminderLogs = reminderLogService.getAcknowledgedReminderLogsBetween(start, end);

        if (reminderLogs.isEmpty()) {
            log.info("No acknowledged reminder logs found between {} and {}", start, end);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} acknowledged reminder logs between {} and {}", reminderLogs.size(), start, end);
        return ResponseEntity.ok(reminderLogs);
    }
}
