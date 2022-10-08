package ru.practicum.service.admin.controllers.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.admin.services.events.AdminEventService;
import ru.practicum.service.entitys.event.model.dto.EventDto;
import ru.practicum.service.entitys.event.model.dto.EventFullDto;
import ru.practicum.service.entitys.event.model.entity.EventStatus;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/admin/events")
public class AdminEventController {

    private final AdminEventService adminEventService;

    public AdminEventController(AdminEventService adminEventService) {
        this.adminEventService = adminEventService;
    }

    @PatchMapping(value = "/{eventId}/publish")
    public EventFullDto publishedEvent(@Positive @PathVariable long eventId) {
        log.info("Опубликовано событие: {}", eventId);
        return adminEventService.publishingEvent(eventId);
    }

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
        log.info("Получен запрос списка пользователей");
        return adminEventService.findAllEventsForAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping(value = "/{eventId}")
    public EventFullDto updateEventAdmin(@Positive @PathVariable long eventId, @RequestBody EventDto eventDto) {
        log.info("Обновлено событие: id: {} event: {}", eventId, eventDto);
        return adminEventService.updateEventAdmin(eventId, eventDto);
    }

    @PatchMapping(value = "/{eventId}/reject")
    public EventFullDto rejectedEvent(@Positive @PathVariable long eventId) {
        log.info("Событие: {} отклонено", eventId);
        return adminEventService.rejectedEvent(eventId);
    }

}
