package com.dls.pcms.infrastructure.repositories;

import com.dls.pcms.application.services.EventService;
import com.dls.pcms.domain.models.Event;
import com.dls.pcms.domain.models.Student;
import com.dls.pcms.domain.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public Event createEvent(Event event) {
        log.info("Creating new event with title: {}", event.getTitle());
        Event savedEvent = eventRepository.save(event);
        log.info("Event created successfully with ID: {}", savedEvent.getEventId());
        return savedEvent;
    }

    @Override
    public Optional<Event> getEventById(UUID eventId) {
        log.info("Fetching event by ID: {}", eventId);
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isPresent()) {
            log.info("Event found with ID: {}", eventId);
        } else {
            log.info("No event found with ID: {}", eventId);
        }
        return event;
    }

    @Override
    public List<Event> getAllEvents() {
        log.info("Fetching all events");
        List<Event> events = eventRepository.findAll();
        log.info("Found {} events", events.size());
        return events;
    }

    @Override
    public Event updateEvent(UUID eventId, Event eventDetails) {
        log.info("Updating event with ID: {}", eventId);
        Optional<Event> existingEventOpt = eventRepository.findById(eventId);

        if (existingEventOpt.isPresent()) {
            Event existingEvent = existingEventOpt.get();
            existingEvent.setTitle(eventDetails.getTitle());
            existingEvent.setDescription(eventDetails.getDescription());
            existingEvent.setEventDate(eventDetails.getEventDate());
            existingEvent.setUpdatedAt(eventDetails.getUpdatedAt());

            Event updatedEvent = eventRepository.save(existingEvent);
            log.info("Event updated successfully with ID: {}", eventId);
            return updatedEvent;
        } else {
            log.error("No event found with ID: {}", eventId);
            return null;
        }
    }

    @Override
    public void deleteEvent(UUID eventId) {
        log.info("Deleting event with ID: {}", eventId);
        eventRepository.deleteById(eventId);
        log.info("Event deleted successfully with ID: {}", eventId);
    }

    @Override
    public List<Event> getEventsByTitle(String title) {
        log.info("Fetching events by title: {}", title);
        List<Event> events = eventRepository.findByTitle(title);
        log.info("Found {} events with title: {}", events.size(), title);
        return events;
    }

    @Override
    public List<Event> getEventsByDate(LocalDate eventDate) {
        log.info("Fetching events by date: {}", eventDate);
        List<Event> events = eventRepository.findByEventDate(eventDate);
        log.info("Found {} events on date: {}", events.size(), eventDate);
        return events;
    }


    public Event findById(UUID eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        return event.orElse(null);
    }

}
