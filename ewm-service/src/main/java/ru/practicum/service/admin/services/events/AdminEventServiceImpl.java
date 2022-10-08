package ru.practicum.service.admin.services.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.service.allstorage.CategoriesRepository;
import ru.practicum.service.allstorage.EventRepository;
import ru.practicum.service.datesetter.DateTimeSetter;
import ru.practicum.service.entitys.event.model.dto.EventDto;
import ru.practicum.service.entitys.event.model.dto.EventFullDto;
import ru.practicum.service.entitys.event.model.dto.EventMapper;
import ru.practicum.service.entitys.event.model.entity.Event;
import ru.practicum.service.entitys.event.model.entity.EventStatus;
import ru.practicum.service.exception.ForbiddenException;
import ru.practicum.service.exception.NotFoundException;
import ru.practicum.service.exception.ValidationException;
import ru.practicum.service.validation.Validation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminEventServiceImpl implements AdminEventService {

    private final EventRepository eventRepository;
    private final CategoriesRepository categoriesRepository;
    private final EventMapper eventMapper;

    public AdminEventServiceImpl(EventRepository eventRepository, CategoriesRepository categoriesRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.categoriesRepository = categoriesRepository;

        this.eventMapper = eventMapper;
    }

    @Override
    @Transactional
    public EventFullDto publishingEvent(long eventId) {
        Event event = eventRepository.getEventById(eventId);
        Validation.validationEvent(event, eventId);
        if (LocalDateTime.now().plusHours(1).isAfter(event.getEventDate())) {
            throw new ForbiddenException(String
                    .format("До начала события ID = %d менее 1 часа, редактирование запрещено", eventId));
        }
        if (event.getState() == EventStatus.PENDING) {
            event.setState(EventStatus.PUBLISHED);
            event.setPublishedOn(DateTimeSetter.setDateTime());
        } else {
            throw new ForbiddenException(String
                    .format("Событие ID = %d не находится в статусе PENDING", eventId));
        }
        return eventMapper.toEventFullDto(eventRepository.saveAndFlush(event));
    }

    @Override
    public List<EventFullDto> findAllEventsForAdmin(List<Long> users, List<EventStatus> states, List<Long> categories,
                                                    LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                    int from, int size) {
        if (users.isEmpty()) {
            throw new ValidationException("Список пользователей пустой");
        }
        if (states.isEmpty()) {
            throw new ValidationException("Список статусов пустой");
        }
        if (categories.isEmpty()) {
            throw new ValidationException("Список категорий пустой");
        }
        Pageable pageable = PageRequest.of(from, size, Sort.by("id"));

        if (rangeStart != null & rangeEnd != null) {
            return eventRepository.getEventsForAdmin(users, states, categories,
                            rangeStart, rangeEnd, pageable).stream().map(eventMapper::toEventFullDto)
                    .collect(Collectors.toList());
        }
        rangeStart = DateTimeSetter.setDateTime();

        return eventRepository.getEventsForAdminWithoutDate(users, states, categories,
                rangeStart, pageable).stream().map(eventMapper::toEventFullDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto updateEventAdmin(long eventId, EventDto eventDto) {
        if (eventDto == null) {
            throw new ValidationException("Событие пустое");
        }
        if (eventRepository.getEventById(eventDto.getEventId()) == null) {
            throw new NotFoundException(String.format("Объект с ID = %d не найден", eventId));
        }
        Event event = eventRepository.getEventById(eventId);

        if (eventDto.getAnnotation() != null) {
            event.setAnnotation(eventDto.getAnnotation());
        }
        if (eventDto.getCategory() != null) {
            event.setCategory(categoriesRepository.findById(eventDto.getCategory()).get());
        }
        if (eventDto.getDescription() != null) {
            event.setDescription(eventDto.getDescription());
        }
        if (eventDto.getEventDate() != null) {
            event.setEventDate(eventDto.getEventDate());
        }
        if (eventDto.getLocation() != null) {
            event.setLocation(eventDto.getLocation());
        }
        if (eventDto.getPaid() != null) {
            event.setPaid(eventDto.getPaid());
        }
        if (eventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventDto.getParticipantLimit());
        }
        if (eventDto.getTitle() != null) {
            event.setTitle(eventDto.getTitle());
        }
        if (eventDto.getRequestModeration() != null) {
            event.setRequestModeration(eventDto.getRequestModeration());
        }
        return eventMapper.toEventFullDto(eventRepository.saveAndFlush(event));
    }

    @Override
    @Transactional
    public EventFullDto rejectedEvent(long eventId) {
        Event event = eventRepository.getEventById(eventId);
        Validation.validationEvent(event, eventId);
        if (event.getState() == EventStatus.PUBLISHED) {
            throw new ForbiddenException(String.format("Событие ID =%d публиковано и не может быть отклонено", eventId));
        }
        event.setState(EventStatus.CANCELED);
        return eventMapper.toEventFullDto(eventRepository.saveAndFlush(event));
    }
}
