package ru.practicum.service.controllers.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.services.admin.AdminEventService;
import ru.practicum.service.models.event.dto.EventDto;
import ru.practicum.service.models.event.dto.EventFullDto;
import ru.practicum.service.models.event.entity.EventStatus;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

/**
 * API for working with events
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/admin/events")
public class AdminEventController {

    private final AdminEventService adminEventService;

    public AdminEventController(AdminEventService adminEventService) {
        this.adminEventService = adminEventService;
    }

    /**
     * Publishing an event
     * @param eventId event id
     * @return EventFullDto contains full information about the event
     */
    @PatchMapping(value = "/{eventId}/publish")
    public EventFullDto publishedEvent(@Positive @PathVariable long eventId) {
        log.info("Опубликовано событие: {}", eventId);
        return adminEventService.publishingEvent(eventId);
    }

    /**
     * The endpoint returns complete information about all events that match the passed conditions
     * @param users list of users
     * @param states list of states
     * @param categories list of categories
     * @param rangeStart date and time not earlier than when the event should occur
     * @param rangeEnd date and time no later than which the event should occur
     * @param from the number of events that need to be skipped to form the current set
     * @param size number of events in the set
     * @return List<EventFullDto> object contains a list of events
     */
    @GetMapping()
    public List<EventFullDto> getEvents(@RequestParam(value = "users") List<Long> users,
                                    @RequestParam(value = "states") List<EventStatus> states,
                                    @RequestParam(value = "categories") List<Long> categories,
                                    @RequestParam(required = false)
                                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                    @RequestParam(required = false)
                                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                    @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        log.info("Получен запрос списка событий");
        return adminEventService.findAllEventsForAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    /**
     * Editing the data of any event by the administrator. Data validation is not required.
     * @param eventId event id
     * @param eventDto contains event
     * @return EventFullDto object contains event
     */
    @PutMapping(value = "/{eventId}")
    public EventFullDto updateEventAdmin(@Positive @PathVariable long eventId, @RequestBody EventDto eventDto) {
        log.info("Обновлено событие: id: {} event: {}", eventId, eventDto);
        return adminEventService.updateEventAdmin(eventId, eventDto);
    }

    /**
     * Event rejection. The event should not be published.
     * @param eventId event id
     * @return EventFullDto object contains event
     */
    @PatchMapping(value = "/{eventId}/reject")
    public EventFullDto rejectedEvent(@Positive @PathVariable long eventId) {
        log.info("Событие: {} отклонено", eventId);
        return adminEventService.rejectedEvent(eventId);
    }
}
