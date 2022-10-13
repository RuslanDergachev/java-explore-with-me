package ru.practicum.statservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statservice.models.EndpointHit;
import ru.practicum.statservice.models.ViewsStatsDto;
import ru.practicum.statservice.service.LogEventsService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * class API for working with statistics
 */
@RestController
@Slf4j
@Validated
public class EventStatController {

    LogEventsService logEventsService;

    public EventStatController(LogEventsService logEventsService) {
        this.logEventsService = logEventsService;
    }

    /**
     * Statistics Saving API
     * @param endpointHit entity statistic
     */
    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.OK)
    public void addNewEndpointHit(@RequestBody EndpointHit endpointHit) {
        log.warn("Запись статистики ip {}", endpointHit.getIp());
        logEventsService.addNewStatEvent(endpointHit);
    }

    /**
     * API for getting statistics on parameters
     * @param start date and time not earlier than when the event should occur
     * @param end date and time no later than which the event should occur
     * @param uris list of uris
     * @param unique boolean parameter
     * @return List<ViewsStatsDto> object
     */
    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewsStatsDto> getLogViews(
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam List<String> uris,
            @RequestParam(required = false) Boolean unique
    ) {
        log.warn("Получен запрос статистики");
        return logEventsService.getViews(start, end, uris, unique);
    }
}