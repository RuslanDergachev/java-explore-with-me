package ru.practicum.service.user.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.entitys.event.model.dto.EventDto;
import ru.practicum.service.entitys.event.model.dto.EventFullDto;
import ru.practicum.service.entitys.event.model.dto.EventShortDto;
import ru.practicum.service.entitys.participation.model.dto.ParticipationRequestDto;
import ru.practicum.service.user.services.UserEventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.websocket.server.PathParam;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserEventService userEventService;

    @Autowired
    public UserController(UserEventService userEventService) {
        this.userEventService = userEventService;
    }

    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto addNewEvent(@Positive @PathVariable long userId, @RequestBody EventDto eventDto) {
        log.info("Создание нового события: {}", eventDto);
        return userEventService.addNewEvent(userId, eventDto);
    }

    @PatchMapping(value = "/{userId}/events")
    public EventFullDto updateEvent(@Positive @PathVariable long userId, @RequestBody EventDto eventDto) {
        log.info("Обновлено событие: {}", eventDto);
        return userEventService.updateEvent(userId, eventDto);
    }

    @PostMapping(value = "/{userId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto createRequest(@Positive @PathVariable long userId,
                                                 @Positive @PathParam("eventId") long eventId) {
        log.info("Добавлен запрос пользователя: {} на участие в событии: {}", userId, eventId);
        return userEventService.createRequest(userId, eventId);
    }

    @PatchMapping(value = "/{userId}/events/{eventId}")
    public EventFullDto cancelledEvent(@Positive @PathVariable long userId,
                                       @Positive @PathVariable long eventId) {
        log.info("Отмена события: {}", eventId);
        return userEventService.cancelledEvent(userId, eventId);
    }

    @GetMapping(value = "/{userId}/events")
    public List<EventShortDto> getEventsByUserId(@PathVariable long userId,
                                                 @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                 @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Получение списка событий, добавленных пользователем: {}", userId);
        return userEventService.getEventsByUserId(userId, from, size);
    }

    @GetMapping(value = "/{userId}/events/{eventId}")
    public EventFullDto getEventByEventIdEndUserId(@Positive @PathVariable long userId,
                                                   @Positive @PathVariable long eventId
    ) {
        log.info("Получение события {}, добавленного пользователем: {}", eventId, userId);
        return userEventService.getEventsByIdAndUserId(userId, eventId);
    }

    @GetMapping(value = "/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsByUser(@Positive @PathVariable long userId,
                                                           @Positive @PathVariable long eventId
    ) {
        log.info("Получение события {}, добавленного пользователем: {}", eventId, userId);
        return userEventService.getRequestsByUser(userId, eventId);
    }

    @PatchMapping(value = "/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmedRequest(@Positive @PathVariable long userId,
                                                    @Positive @PathVariable long eventId,
                                                    @Positive @PathVariable long reqId) {
        log.info("Подтверждение заявки: {}, на участие в событии {}", reqId, eventId);
        return userEventService.confirmedRequest(userId, eventId, reqId);
    }

    @PatchMapping(value = "/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@Positive @PathVariable long userId,
                                                 @Positive @PathVariable long eventId,
                                                 @Positive @PathVariable long reqId) {
        log.info("Подтверждение заявки: {}, на участие в событии {}", reqId, eventId);
        return userEventService.rejectRequest(userId, eventId, reqId);
    }

    @GetMapping(value = "/{userId}/requests")
    public List<ParticipationRequestDto> getOtherRequestsByUser(@Positive @PathVariable long userId) {
        log.info("Получение заявок пользователя {} на участие в чужих событиях", userId);
        return userEventService.getOtherRequestsByUser(userId);
    }

    @PatchMapping(value = "/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@Positive @PathVariable long userId,
                                                 @Positive @PathVariable long requestId) {
        log.info("Отмена заявки: {}, на участие в событии {}", requestId, userId);
        return userEventService.cancelRequest(userId, requestId);
    }
}
