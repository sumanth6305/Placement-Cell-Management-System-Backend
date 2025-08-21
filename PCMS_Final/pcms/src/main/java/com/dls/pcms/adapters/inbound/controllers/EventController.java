package com.dls.pcms.adapters.inbound.controllers;

import com.dls.pcms.application.services.FeedbackService;
import com.dls.pcms.domain.models.Event;
import com.dls.pcms.application.services.EventService;
import com.dls.pcms.domain.models.Feedback;
import com.dls.pcms.domain.models.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
@Slf4j
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Event> createEvent(@Validated @RequestBody Event event) {
        log.info("Received request to create event with title: {}", event.getTitle());

        Event createdEvent = eventService.createEvent(event);

        if (createdEvent == null) {
            log.error("Event creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        log.info("Event created successfully with ID: {}", createdEvent.getEventId());
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable UUID eventId) {
        log.info("Received request to get event by ID: {}", eventId);

        Optional<Event> event = eventService.getEventById(eventId);

        if (event.isEmpty()) {
            log.info("No event found with ID: {}", eventId);
            return ResponseEntity.notFound().build();
        }

        log.info("Returning event with ID: {}", eventId);
        return ResponseEntity.ok(event.get());
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        log.info("Received request to get all events");

        List<Event> events = eventService.getAllEvents();

        if (events.isEmpty()) {
            log.info("No events found");
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} events", events.size());
        return ResponseEntity.ok(events);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable UUID eventId,
            @Validated @RequestBody Event eventDetails) {
        log.info("Received request to update event with ID: {}", eventId);

        Event updatedEvent = eventService.updateEvent(eventId, eventDetails);

        if (updatedEvent == null) {
            log.info("Event update failed. No event found with ID: {}", eventId);
            return ResponseEntity.notFound().build();
        }

        log.info("Event updated successfully with ID: {}", eventId);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable UUID eventId) {
        log.info("Received request to delete event with ID: {}", eventId);

        eventService.deleteEvent(eventId);

        log.info("Event deleted successfully with ID: {}", eventId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Event>> getEventsByTitle(@PathVariable String title) {
        log.info("Received request to get events by title: {}", title);

        List<Event> events = eventService.getEventsByTitle(title);

        if (events.isEmpty()) {
            log.info("No events found with title: {}", title);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} events with title: {}", events.size(), title);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/date/{eventDate}")
    public ResponseEntity<List<Event>> getEventsByDate(@PathVariable LocalDate eventDate) {
        log.info("Received request to get events by date: {}", eventDate);

        List<Event> events = eventService.getEventsByDate(eventDate);

        if (events.isEmpty()) {
            log.info("No events found on date: {}", eventDate);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} events on date: {}", events.size(), eventDate);
        return ResponseEntity.ok(events);
    }


    @PostMapping("/{eventId}/feedback")
    public ResponseEntity<Feedback> createFeedback(@Validated @RequestBody Feedback feedback) {
        log.info("Received request to create feedback with type: {}", feedback.getType());

        Feedback createdFeedback = feedbackService.createFeedback(feedback);

        if (createdFeedback == null) {
            log.error("Feedback creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        log.info("Feedback created successfully with ID: {}", createdFeedback.getFeedbackId());
        return new ResponseEntity<>(createdFeedback, HttpStatus.CREATED);
    }


    @GetMapping("/{eventId}/feedback")
    public ResponseEntity<Page<Feedback>> getAllFeedbacks(@PathVariable UUID eventId,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Event event = eventService.findById(eventId);
        if (event== null) {
            return ResponseEntity.notFound().build();
        }
        log.info("Received request to get job applications with pagination - Page: {}, Size: {}", page, size);
        //log.info("Received request to get all feedbacks");
        Pageable pageable = PageRequest.of(page, size);
        Page<Feedback> feedbacks = feedbackService.getAllFeedbacksPage(pageable);

        if (feedbacks.isEmpty()) {
            log.info("No feedbacks found");
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} feedbacks", feedbacks.getTotalElements());
        return ResponseEntity.ok(feedbacks);
    }


}
