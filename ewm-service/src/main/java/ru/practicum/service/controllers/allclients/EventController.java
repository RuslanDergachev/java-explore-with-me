package ru.practicum.service.controllers.allclients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.services.allclients.EventService;
import ru.practicum.service.webclient.EventClient;
import ru.practicum.service.models.event.dto.EventFullDto;
import ru.practicum.service.models.event.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The class contains methods for working with events and requests to participate in events for unregistered users
 */
@RestController
@RequestMapping(path = "/events")
@Slf4j
@Validated
public class EventController {
    private final EventClient eventClient;
    private final EventService eventService;

    public EventController(EventClient eventClient, EventService eventService) {
        this.eventClient = eventClient;
        this.eventService = eventService;
    }

    /**
     * The method returns information about the requested event for an unregistered user
     * @param eventId id created Event
     * @param request HttpServletRequest object
     * @return EventFullDto object contains information about the event
     */
    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable long eventId, HttpServletRequest request) {
        log.info("client ip: {}", request.getRemoteAddr());
        log.info("endpoint path: {}", request.getRequestURI());
        eventClient.addViews(request);
        return eventService.getEventById(eventId);
    }

    /**
     * The method returns a list of events, by optional sampling parameters for unregistered users
     * @param request HttpServletRequest object
     * @param text parameter for searching by annotation and detailed description
     * @param categories list of event categories
     * @param paid search for paid/free events only
     * @param rangeStart date and time not earlier than when the event should occur
     * @param rangeEnd date and time no later than which the event should occur
     * @param onlyAvailable only events that have not reached the limit of participation requests
     * @param sort Sorting option: by event date or by number of views
     * @param from the number of events that need to be skipped to form the current set
     * @param size number of events in the set
     * @return List<EventShortDto> object contains short information about events
     */
    @GetMapping
    public List<EventShortDto> getEvents(HttpServletRequest request,
                                             @RequestParam String text,
                                             @RequestParam List<Long> categories, @RequestParam boolean paid,
                                             @RequestParam(required = false)
                                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                             @RequestParam(required = false)
                                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                             @RequestParam Boolean onlyAvailable, @RequestParam String sort,
                                             @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer from,
                                             @Positive @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.warn("Получен запрос списка событий {}", text);
        log.info("client ip: {}", request.getRemoteAddr());
        log.info("endpoint path: {}", request.getRequestURI());
        eventClient.addViews(request);
        return eventService.searchEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, from, size);
    }
}
