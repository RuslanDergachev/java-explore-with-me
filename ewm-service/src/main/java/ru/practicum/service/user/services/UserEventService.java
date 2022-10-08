package ru.practicum.service.user.services;

import ru.practicum.service.entitys.event.model.dto.EventDto;
import ru.practicum.service.entitys.event.model.dto.EventFullDto;
import ru.practicum.service.entitys.event.model.dto.EventShortDto;
import ru.practicum.service.entitys.participation.model.dto.ParticipationRequestDto;

import java.util.List;

public interface UserEventService {

    EventFullDto addNewEvent(long userId, EventDto eventDto);

    EventFullDto updateEvent(long userId, EventDto eventDto);

    ParticipationRequestDto createRequest(long userId, long eventId);

    EventFullDto cancelledEvent(long userId, long eventId);

    List<EventShortDto> getEventsByUserId(long userId, int from, int size);

    EventFullDto getEventsByIdAndUserId(long userId, long eventId);

    List<ParticipationRequestDto> getRequestsByUser(long userId, long eventId);

    ParticipationRequestDto confirmedRequest(long userId, long eventId, long reqId);

    ParticipationRequestDto rejectRequest(long userId, long eventId, long reqId);

    List<ParticipationRequestDto> getOtherRequestsByUser(long userId);

    ParticipationRequestDto cancelRequest(long userId, long requestId);
}
