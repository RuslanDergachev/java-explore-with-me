package ru.practicum.service.controllers.registeredclients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.models.event.dto.EventDto;
import ru.practicum.service.models.event.dto.EventFullDto;
import ru.practicum.service.models.event.dto.EventShortDto;
import ru.practicum.service.models.participation.dto.ParticipationRequestDto;
import ru.practicum.service.services.registeredclients.UserEventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * The class contains methods for working with events and requests for participation in events for registered users
 */
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

    /**
     * Method create new Event for a registered user and saves in database
     * @param userId id registered User
     * @param eventDto object new Event
     * @return EventFullDto object contains information about the event
     */
    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto addNewEvent(@Positive @PathVariable long userId, @RequestBody EventDto eventDto) {
        log.info("Создание нового события: {}", eventDto);
        return userEventService.addNewEvent(userId, eventDto);
    }

    /**
     * Method update Event for a registered user and saves in database
     * @param userId id registered User
     * @param eventDto id created Event
     * @return EventFullDto object contains information about the event
     */
    @PatchMapping(value = "/{userId}/events")
    public EventFullDto updateEvent(@Positive @PathVariable long userId, @RequestBody EventDto eventDto) {
        log.info("Обновлено событие: {}", eventDto);
        return userEventService.updateEvent(userId, eventDto);
    }

    /**
     * The method creates a request to participate in a published event
     * @param userId id registered User
     * @param eventId id created Event
     * @return ParticipationRequestDto object contains information about the participation
     */
    @PostMapping(value = "/{userId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto createRequest(@Positive @PathVariable long userId,
                                                 @Positive @PathParam("eventId") long eventId) {
        log.info("Добавлен запрос пользователя: {} на участие в событии: {}", userId, eventId);
        return userEventService.createRequest(userId, eventId);
    }

    /**
     * The method cancels an event created by a registered user
     * @param userId id registered User
     * @param eventId id created Event
     * @return EventFullDto object contains information about the event
     */
    @PatchMapping(value = "/{userId}/events/{eventId}")
    public EventFullDto cancelledEvent(@Positive @PathVariable long userId,
                                       @Positive @PathVariable long eventId) {
        log.info("Отмена события: {}", eventId);
        return userEventService.cancelledEvent(userId, eventId);
    }

    /**
     * The method returns a list of events added by the registered user
     * @param userId id registered User
     * @param from the number of the start page of the list, optional parameter
     * @param size number of entries per page, optional parameter
     * @return List<EventShortDto> object contains information about the list event
     */
    @GetMapping(value = "/{userId}/events")
    public List<EventShortDto> getEventsByUserId(@PathVariable long userId,
                                                 @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                 @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Получение списка событий, добавленных пользователем: {}", userId);
        return userEventService.getEventsByUserId(userId, from, size);
    }

    /**
     * The method returns an event added by the current user
     * @param userId id registered User
     * @param eventId id created Event
     * @return EventFullDto object contains information about the event
     */
    @GetMapping(value = "/{userId}/events/{eventId}")
    public EventFullDto getEventByEventIdEndUserId(@Positive @PathVariable long userId,
                                                   @Positive @PathVariable long eventId
    ) {
        log.info("Получение события {}, добавленного пользователем: {}", eventId, userId);
        return userEventService.getEventsByIdAndUserId(userId, eventId);
    }

    /**
     * The method returns a list of requests to participate in the event of the current user
     * @param userId id registered User
     * @param eventId id created Event
     * @return List<ParticipationRequestDto> object contains information about the list participation
     */
    @GetMapping(value = "/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsByUser(@Positive @PathVariable long userId,
                                                           @Positive @PathVariable long eventId
    ) {
        log.info("Получение запросов на участие в событии {}, добавленном пользователем: {}", eventId, userId);
        return userEventService.getRequestsByUser(userId, eventId);
    }

    /**
     * The method changes the status of the application for participation in the event to confirmed
     * @param userId id registered User
     * @param eventId id created Event
     * @param reqId id created request
     * @return ParticipationRequestDto object contains information about the participation
     */
    @PatchMapping(value = "/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmedRequest(@Positive @PathVariable long userId,
                                                    @Positive @PathVariable long eventId,
                                                    @Positive @PathVariable long reqId) {
        log.info("Подтверждение заявки: {}, на участие в событии {}", reqId, eventId);
        return userEventService.confirmedRequest(userId, eventId, reqId);
    }

    /**
     * Еhe method changes the status of the application for participation in the event to rejected
     * @param userId id registered User
     * @param eventId id created Event
     * @param reqId id created request
     * @return ParticipationRequestDto object contains information about the participation
     */
    @PatchMapping(value = "/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@Positive @PathVariable long userId,
                                                 @Positive @PathVariable long eventId,
                                                 @Positive @PathVariable long reqId) {
        log.info("Отклонение заявки: {}, на участие в событии {}", reqId, eventId);
        return userEventService.rejectRequest(userId, eventId, reqId);
    }

    /**
     * The method returns a list of requests from the current user to participate in other people's events
     * @param userId id registered User
     * @return List<ParticipationRequestDto> object contains information about the list participation
     */
    @GetMapping(value = "/{userId}/requests")
    public List<ParticipationRequestDto> getOtherRequestsByUser(@Positive @PathVariable long userId) {
        log.info("Получение заявок пользователя {} на участие в чужих событиях", userId);
        return userEventService.getOtherRequestsByUser(userId);
    }

    /**
     * The method cancels the current user's request to participate in the event
     * @param userId id registered User
     * @param requestId id created request
     * @return ParticipationRequestDto object contains information about the list participation
     */
    @PatchMapping(value = "/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@Positive @PathVariable long userId,
                                                 @Positive @PathVariable long requestId) {
        log.info("Отмена заявки: {}, на участие в событии {}", requestId, userId);
        return userEventService.cancelRequest(userId, requestId);
    }
}
