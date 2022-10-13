package ru.practicum.service.services.admin;

import ru.practicum.service.models.event.dto.EventDto;
import ru.practicum.service.models.event.dto.EventFullDto;
import ru.practicum.service.models.event.entity.EventStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * A class for working with events
 */
public interface AdminEventService {

    /**
     * Publishing events
     * @param eventDto entity event
     * @return EventFullDto object publishing event
     */
    EventFullDto publishingEvent(long eventDto);

    /**
     * The method returns complete information about all events that match the passed conditions
     * @param users list of users
     * @param states list of states
     * @param categories list of categories
     * @param rangeStart date and time not earlier than when the event should occur
     * @param rangeEnd date and time no later than which the event should occur
     * @param from the number of events that need to be skipped to form the current set
     * @param size number of events in the set
     * @return List<EventFullDto> object list of events
     */
    List<EventFullDto> findAllEventsForAdmin(List<Long> users, List<EventStatus> states, List<Long> categories,
                                             LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                             int from, int size);

    /**
     * Event update method
     * @param eventId event id
     * @param eventDto entity event
     * @return EventFullDto object updated event
     */
    EventFullDto updateEventAdmin(long eventId, EventDto eventDto);

    /**
     * Event rejection. The event should not be published
     * @param eventId event id
     * @return EventFullDto object
     */
    EventFullDto rejectedEvent(long eventId);
}
