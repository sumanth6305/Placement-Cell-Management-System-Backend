package com.dls.pcms.application.services;

import com.dls.pcms.domain.models.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventService {

    Event createEvent(Event event);

    Optional<Event> getEventById(UUID eventId);

    List<Event> getAllEvents();

    Event updateEvent(UUID eventId, Event eventDetails);

    void deleteEvent(UUID eventId);

    List<Event> getEventsByTitle(String title);

    List<Event> getEventsByDate(LocalDate eventDate);

    Event findById(UUID eventId);

    // Add more custom methods as needed
}
