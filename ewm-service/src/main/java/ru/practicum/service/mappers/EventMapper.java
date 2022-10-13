package ru.practicum.service.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.service.models.event.dto.EventDto;
import ru.practicum.service.models.event.dto.EventFullDto;
import ru.practicum.service.models.event.dto.EventShortDto;
import ru.practicum.service.models.event.dto.UpdateEventRequest;
import ru.practicum.service.webclient.EventClient;
import ru.practicum.service.models.event.entity.Event;
import ru.practicum.service.models.locations.dto.LocationDto;
import ru.practicum.statservice.models.ViewsStatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {

    private final EventClient eventClient;

    public EventMapper(EventClient eventClient) {
        this.eventClient = eventClient;
    }

    public static EventDto toEventDto(Event event) {
        return EventDto.builder()
                .annotation(event.getAnnotation())
                .category(event.getCategory().getId())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .eventId(event.getId())
                .location(event.getLocation())
                .title(event.getTitle())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .build();
    }

    public static Event toEvent(EventDto eventDto) {
        return Event.builder()
                .annotation(eventDto.getAnnotation())
                .category(null)
                .confirmedRequests(0L)
                .createdOn(null)
                .description(eventDto.getDescription())
                .eventDate(eventDto.getEventDate())
                .id(eventDto.getEventId())
                .initiator(null)
                .location(eventDto.getLocation())
                .paid(eventDto.getPaid())
                .participantLimit(eventDto.getParticipantLimit())
                .publishedOn(null)
                .requestModeration(eventDto.getRequestModeration())
                .state(null)
                .title(eventDto.getTitle())
                .views(null)
                .build();
    }

    public EventFullDto toEventFullDto(Event event) {
        LocationDto locationDto = LocationDto.builder()
                .lon(event.getLocation().getLon())
                .lat(event.getLocation().getLat())
                .build();
        EventFullDto eventFullDto = EventFullDto.builder()
                .annotation(event.getAnnotation())
                .category(event.getCategory())
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(event.getInitiator())
                .location(locationDto)
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
        setViewsEventFullDto(event, eventFullDto);
        return eventFullDto;
    }

    public EventShortDto toEventShortDto(Event event) {
        EventShortDto eventShortDto = EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(event.getCategory())
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
        setViewsEventShortDto(event, eventShortDto);
        return eventShortDto;
    }

    public static Event toEvent(EventShortDto eventShortDto) {
        return Event.builder()
                .id(eventShortDto.getId())
                .annotation(eventShortDto.getAnnotation())
                .category(eventShortDto.getCategory())
                .confirmedRequests(eventShortDto.getConfirmedRequests())
                .eventDate(eventShortDto.getEventDate())
                .initiator(UserMapper.toUser(eventShortDto.getInitiator()))
                .paid(eventShortDto.getPaid())
                .title(eventShortDto.getTitle())
                .views(eventShortDto.getViews())
                .build();
    }

    public static Event toEvent(UpdateEventRequest updateEventRequest) {
        return Event.builder()
                .id(updateEventRequest.getEventId())
                .annotation(updateEventRequest.getAnnotation())
                .category(null)
                .confirmedRequests(null)
                .eventDate(updateEventRequest.getEventDate())
                .initiator(null)
                .paid(updateEventRequest.getPaid())
                .title(updateEventRequest.getTitle())
                .views(null)
                .build();
    }

    private void setViewsEventShortDto(Event event, EventShortDto eventShortDto) {
        String uri = "/events/" + event.getId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<ViewsStatsDto> views = eventClient.getViews(
                event.getCreatedOn().format(formatter),
                LocalDateTime.now().format(formatter),
                Collections.singletonList(uri),
                false);
        if (!views.isEmpty()) {
            ViewsStatsDto view = views
                    .stream()
                    .filter(viewStats -> viewStats.getUri().equals(uri))
                    .findAny().orElseThrow();
            eventShortDto.setViews(view.getHits());
        }
    }

    private void setViewsEventFullDto(Event event, EventFullDto eventFullDto) {
        String uri = "/events/" + event.getId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<ViewsStatsDto> views = eventClient.getViews(
                event.getCreatedOn().format(formatter),
                LocalDateTime.now().format(formatter),
                Collections.singletonList(uri),
                false);
        if (!views.isEmpty()) {
            ViewsStatsDto view = views
                    .stream()
                    .filter(viewStats -> viewStats.getUri().equals(uri))
                    .findAny().orElseThrow();
            eventFullDto.setViews(view.getHits());
        }
    }

    public List<EventShortDto> toListEventsDto(List<Event> events) {
        return events.stream().map(this::toEventShortDto).collect(Collectors.toList());
    }

    public static List<Event> toListEvents(List<EventShortDto> eventsShortDto) {
        return eventsShortDto.stream().map(EventMapper::toEvent).collect(Collectors.toList());
    }
}
