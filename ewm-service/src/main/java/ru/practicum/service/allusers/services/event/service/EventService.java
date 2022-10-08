package ru.practicum.service.allusers.services.event.service;

import ru.practicum.service.entitys.event.model.dto.EventDto;
import ru.practicum.service.entitys.event.model.dto.EventFullDto;
import ru.practicum.service.entitys.event.model.dto.EventShortDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    EventDto addNewEvent(long userId, EventDto eventDto);

    EventFullDto getEventById(long eventId);

    List<EventShortDto> searchEvents(String text, List<Long> categories, boolean paid,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                         boolean onlyAvailable, String sort,
                                         Integer from, Integer size);
}
