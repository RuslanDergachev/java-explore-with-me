package ru.practicum.service.services.allclients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.service.repositories.EventRepository;
import ru.practicum.service.datesetter.DateTimeSetter;
import ru.practicum.service.models.event.dto.EventDto;
import ru.practicum.service.models.event.dto.EventFullDto;
import ru.practicum.service.mappers.EventMapper;
import ru.practicum.service.models.event.dto.EventShortDto;
import ru.practicum.service.models.event.entity.Event;
import ru.practicum.service.exception.NotFoundException;
import ru.practicum.service.exception.ValidationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    @Transactional
    public EventDto addNewEvent(long userId, EventDto eventDto) {
        if (eventDto == null) {
            log.warn("Запрос на создание события отсутствует");
            throw new NotFoundException("Запрос на бронирование отсутствует");
        }
        Event event = new Event();
        return EventMapper.toEventDto(eventRepository.saveAndFlush(event));
    }

    @Override
    public EventFullDto getEventById(long eventId) {
        return eventMapper.toEventFullDto(eventRepository.getEventByIdBOrderByState(eventId));
    }

    @Override
    public List<EventShortDto> searchEvents(String text, List<Long> categories, boolean paid, LocalDateTime rangeStart,
                                            LocalDateTime rangeEnd, boolean onlyAvailable, String sort,
                                            Integer from, Integer size) {
        if (text.isEmpty()) {
            log.warn("Отсутствует текст запроса");
            throw new ValidationException("Отсутствует текст запроса");
        }
        if (categories.isEmpty()) {
            log.warn("Отсутствует список категорий в запросе");
            throw new ValidationException("Отсутствует текст запроса");
        }

        if (sort.equals("EVENT_DATE")) {
            sort = "eventDate";
        }
        if (sort.equals("VIEWS")) {
            sort = "views";
        }
        Pageable pageable = PageRequest.of(from, size, Sort.by(sort));

        if (rangeStart != null & rangeEnd != null) {
            return eventRepository.searchEvents(text, categories,
                            paid, rangeStart, rangeEnd, pageable).stream()
                    .map(eventMapper::toEventShortDto).collect(Collectors.toList());
        }
        rangeStart = DateTimeSetter.setDateTime();
        return eventRepository.searchEventsWithoutDate(text, categories,
                paid, rangeStart, pageable).stream().map(eventMapper::toEventShortDto).collect(Collectors.toList());
    }
}
