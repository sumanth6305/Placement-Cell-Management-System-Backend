package com.dls.pcms.domain.repository;

import com.dls.pcms.domain.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    List<Event> findByTitle(String title);

    List<Event> findByEventDate(LocalDate eventDate);

    List<Event> findByDescriptionContaining(String keyword);

    // Add custom methods as needed

    default List<Event> findByTitleOrDescription(String title, String description) {
        // Custom logic to find events by title or description
        List<Event> eventList = null;
        // Implement your custom logic here
        return eventList;
    }
}
