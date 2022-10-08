package ru.practicum.service.user.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.service.allstorage.CategoriesRepository;
import ru.practicum.service.allstorage.EventRepository;
import ru.practicum.service.allstorage.ParticipationRepository;
import ru.practicum.service.allstorage.UserRepository;
import ru.practicum.service.datesetter.DateTimeSetter;
import ru.practicum.service.entitys.event.model.dto.EventDto;
import ru.practicum.service.entitys.event.model.dto.EventFullDto;
import ru.practicum.service.entitys.event.model.dto.EventMapper;
import ru.practicum.service.entitys.event.model.dto.EventShortDto;
import ru.practicum.service.entitys.event.model.entity.Event;
import ru.practicum.service.entitys.event.model.entity.EventStatus;
import ru.practicum.service.entitys.participation.model.dto.ParticipationRequestDto;
import ru.practicum.service.entitys.participation.model.dto.RequestMapper;
import ru.practicum.service.entitys.participation.model.entity.Participation;
import ru.practicum.service.entitys.participation.model.entity.ParticipationStatus;
import ru.practicum.service.entitys.user.model.entity.User;
import ru.practicum.service.exception.FalseIdException;
import ru.practicum.service.exception.NotFoundException;
import ru.practicum.service.exception.ValidationException;
import ru.practicum.service.validation.Validation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserEventServiceImpl implements UserEventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoriesRepository categoriesRepository;
    private final ParticipationRepository participationRepository;
    private final EventMapper eventMapper;

    public UserEventServiceImpl(EventRepository eventRepository, UserRepository userRepository,
                                CategoriesRepository categoriesRepository,
                                ParticipationRepository participationRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.categoriesRepository = categoriesRepository;
        this.participationRepository = participationRepository;
        this.eventMapper = eventMapper;
    }

    @Override
    @Transactional
    public EventFullDto addNewEvent(long userId, EventDto eventDto) {
        validateUserById(userId);
        Validation.validateEventDto(eventDto);
        if (eventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationException("Дата события указана неверно и не может " +
                    "быть ранее чем текущее время плюс 2 часа");
        }
        User user = userRepository.getUserById(userId);
        Event event = EventMapper.toEvent(eventDto);
        event.setCategory(categoriesRepository.findById(eventDto.getCategory()).get());
        event.setState(EventStatus.PENDING);
        event.setCreatedOn(DateTimeSetter.setDateTime());
        event.setInitiator(user);

        return eventMapper.toEventFullDto(eventRepository.saveAndFlush(event));
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(long userId, EventDto eventDto) {
        validateUserById(userId);
        Validation.validateEventDto(eventDto);
        if (eventRepository.getEventById(eventDto.getEventId()) == null) {
            throw new NotFoundException("Событие не найдено");
        }
        Event event = eventRepository.getEventById(eventDto.getEventId());
        if (event.getState() == EventStatus.PUBLISHED) {
            throw new ValidationException("Редактирование события запрещено");
        }
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
        if (eventDto.getPaid() != null) {
            event.setPaid(eventDto.getPaid());
        }
        if (eventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventDto.getParticipantLimit());
        }
        if (eventDto.getTitle() != null) {
            event.setTitle(eventDto.getTitle());
        }
        return eventMapper.toEventFullDto(eventRepository.saveAndFlush(event));
    }

    @Override
    @Transactional
    public ParticipationRequestDto createRequest(long userId, long eventId) {
        Event event = eventRepository.getEventById(eventId);
        Validation.validationEvent(event, eventId);
        if (participationRepository.getParticipationByEventIdAndAndRequesterId(eventId, userId) != null) {
            throw new ValidationException("Подача повторной заявки невозможна");
        }
        if (event.getInitiator().getId() == userId) {
            throw new ValidationException("Невозможно подать заявку на участие в собственном событии");
        }
        if (!event.getState().toString().equals(EventStatus.PUBLISHED.toString())) {
            throw new ValidationException("Невозможно подать заявку на участие в неопубликованном событии");
        }
        if (event.getConfirmedRequests() != 0 && event.getParticipantLimit() != 0
                && event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ValidationException("Достигнут лимит запросов на участие в событии");
        }
        Participation participation = new Participation();
        participation.setCreated(DateTimeSetter.setDateTime());
        participation.setEvent(event);
        participation.setRequester(userRepository.getUserById(userId));
        participation.setStatus(ParticipationStatus.PENDING);
        if (!event.getRequestModeration()) {
            participation.setStatus(ParticipationStatus.CONFIRMED);
            event.setConfirmedRequests(+1L);
            eventRepository.saveAndFlush(event);
        }
        return RequestMapper.toParticipationRequestDto(participationRepository.saveAndFlush(participation));
    }

    @Override
    @Transactional
    public EventFullDto cancelledEvent(long userId, long eventId) {
        validateUserById(userId);
        if (eventRepository.getEventById(eventId) == null) {
            throw new NotFoundException("Событие не найдено");
        }
        Event event = eventRepository.getEventById(eventId);
        Validation.validationEvent(event, eventId);
        if (event.getState() == EventStatus.PUBLISHED || event.getState() == EventStatus.CANCELED) {
            throw new ValidationException("Редактирование события запрещено");
        }
        if (event.getInitiator().getId() != userId) {
            throw new FalseIdException("Редактирование события возможно только создателем");
        }
        event.setState(EventStatus.CANCELED);

        return eventMapper.toEventFullDto(eventRepository.saveAndFlush(event));
    }

    @Override
    public List<EventShortDto> getEventsByUserId(long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from, size, Sort.by("id"));
        return eventRepository.findEventByInitiator(userId, pageable).stream()
                .map(eventMapper::toEventShortDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto getEventsByIdAndUserId(long userId, long eventId) {
        return eventMapper.toEventFullDto(eventRepository.findEventByIdAndUserId(eventId, userId));
    }

    @Override
    public List<ParticipationRequestDto> getRequestsByUser(long userId, long eventId) {
        return participationRepository.getParticipationByEventId(userId, eventId).stream()
                .map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirmedRequest(long userId, long eventId, long reqId) {
        Event event = eventRepository.getEventById(eventId);
        Validation.validationEvent(event, eventId);
        Participation participation = participationRepository.getReferenceById(reqId);
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            participation.setStatus(ParticipationStatus.CONFIRMED);
            event.setConfirmedRequests(+1L);
            eventRepository.saveAndFlush(event);
            return RequestMapper.toParticipationRequestDto(participationRepository.saveAndFlush(participation));
        }
        if (event.getConfirmedRequests() < event.getParticipantLimit() - 1) {
            participation.setStatus(ParticipationStatus.CONFIRMED);
            event.setConfirmedRequests(+1L);
            eventRepository.saveAndFlush(event);
            return RequestMapper.toParticipationRequestDto(participationRepository.saveAndFlush(participation));
        }
        if (event.getConfirmedRequests() + 1 == event.getParticipantLimit()) {
            participation.setStatus(ParticipationStatus.CONFIRMED);
            ParticipationRequestDto participationRequestDto = RequestMapper
                    .toParticipationRequestDto(participationRepository
                            .saveAndFlush(participation));
            event.setConfirmedRequests(+1L);
            eventRepository.saveAndFlush(event);
            List<Participation> participations = participationRepository
                    .findAllByStatus(ParticipationStatus.PENDING.toString());
            for (Participation participation1 : participations) {
                participation1.setStatus(ParticipationStatus.REJECTED);
                participationRepository.saveAndFlush(participation1);
            }
            return participationRequestDto;
        }
        throw new ValidationException("Подтверждение заявки невозможно");
    }

    @Override
    public ParticipationRequestDto rejectRequest(long userId, long eventId, long reqId) {
        Participation participation = participationRepository.getReferenceById(reqId);
        if (participation.getStatus() != ParticipationStatus.PENDING) {
            throw new ValidationException("Заявка не находится в статусе рассмотрения");
        }
        participation.setStatus(ParticipationStatus.REJECTED);
        return RequestMapper.toParticipationRequestDto(participationRepository.saveAndFlush(participation));
    }

    @Override
    public List<ParticipationRequestDto> getOtherRequestsByUser(long userId) {
        return participationRepository.getParticipationByUserId(userId).stream()
                .map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto cancelRequest(long userId, long requestId) {
        Participation participation = participationRepository.getReferenceById(requestId);
        Event event = eventRepository.getEventById(participation.getEvent().getId());
        if (participation.getRequester().getId() == userId) {
            participation.setStatus(ParticipationStatus.CANCELED);
            event.setConfirmedRequests(-1L);
            eventRepository.saveAndFlush(event);
            return RequestMapper.toParticipationRequestDto(participationRepository.saveAndFlush(participation));
        }
        throw new ValidationException("Отменить возможно только свой запрос на участие в событии");
    }

    public void validateUserById(long userId) {
        if (userRepository.getUserById(userId) == null) {
            log.warn("Пользователя с ID {} не существует", userId);
            throw new FalseIdException("Пользователя с ID " + userId + " не существует");
        }
    }
}
