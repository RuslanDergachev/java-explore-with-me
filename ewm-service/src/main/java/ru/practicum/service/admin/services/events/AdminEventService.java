package ru.practicum.service.admin.services.events;

import ru.practicum.service.entitys.event.model.dto.EventDto;
import ru.practicum.service.entitys.event.model.dto.EventFullDto;
import ru.practicum.service.entitys.event.model.entity.EventStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {

    EventFullDto publishingEvent(long eventDto);

    List<EventFullDto> findAllEventsForAdmin(List<Long> users, List<EventStatus> states, List<Long> categories,
                                             LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                             int from, int size);

    EventFullDto updateEventAdmin(long eventId, EventDto eventDto);

    EventFullDto rejectedEvent(long eventId);
}
