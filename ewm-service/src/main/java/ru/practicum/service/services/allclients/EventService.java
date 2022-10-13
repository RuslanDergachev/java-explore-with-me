package ru.practicum.service.services.allclients;

import ru.practicum.service.models.event.dto.EventDto;
import ru.practicum.service.models.event.dto.EventFullDto;
import ru.practicum.service.models.event.dto.EventShortDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * A class for working with events by unregistered users
 */
public interface EventService {

    /**
     * Method for adding a new event
     * @param userId user id
     * @param eventDto entity event
     * @return EventDto object new event
     */
    EventDto addNewEvent(long userId, EventDto eventDto);

    /**
     * Method for getting an event by ID
     * @param eventId event id
     * @return EventFullDto object full entity event
     */
    EventFullDto getEventById(long eventId);

    /**
     * Method of receiving an event with the possibility of filtering
     * @param text text to search in the contents of the annotation and a detailed description of the event
     * @param categories the list of ids of the categories in which the search will be conducted
     * @param paid search for paid/free events only
     * @param rangeStart date and time not earlier than when the event should occur
     * @param rangeEnd date and time no later than which the event should occur
     * @param onlyAvailable only events that have not reached the limit of participation requests
     * @param sort Sorting option: by event date or by number of views
     * @param from the number of events that need to be skipped to form the current set
     * @param size number of events in the set
     * @return List<EventShortDto> object list of short events
     */
    List<EventShortDto> searchEvents(String text, List<Long> categories, boolean paid,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                         boolean onlyAvailable, String sort,
                                         Integer from, Integer size);
}
