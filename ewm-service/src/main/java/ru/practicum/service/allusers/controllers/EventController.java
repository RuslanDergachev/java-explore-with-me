package ru.practicum.service.allusers.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.allusers.services.event.service.EventService;
import ru.practicum.service.client.EventClient;
import ru.practicum.service.entitys.event.model.dto.EventFullDto;
import ru.practicum.service.entitys.event.model.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping("/{eventId}")//доработать метод
    public EventFullDto getEventById(@PathVariable long eventId, HttpServletRequest request) {
        log.info("client ip: {}", request.getRemoteAddr());
        log.info("endpoint path: {}", request.getRequestURI());
        eventClient.addViews(request);
        return eventService.getEventById(eventId);
    }

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
