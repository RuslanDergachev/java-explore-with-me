package ru.practicum.service.services.registeredclients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.service.exception.ForbiddenException;
import ru.practicum.service.mappers.CommentMapper;
import ru.practicum.service.models.comments.dto.CommentDto;
import ru.practicum.service.models.comments.entity.Comment;
import ru.practicum.service.repositories.*;
import ru.practicum.service.datesetter.DateTimeSetter;
import ru.practicum.service.models.event.dto.EventDto;
import ru.practicum.service.models.event.dto.EventFullDto;
import ru.practicum.service.mappers.EventMapper;
import ru.practicum.service.models.event.dto.EventShortDto;
import ru.practicum.service.models.event.entity.Event;
import ru.practicum.service.models.event.entity.EventStatus;
import ru.practicum.service.models.participation.dto.ParticipationRequestDto;
import ru.practicum.service.mappers.RequestMapper;
import ru.practicum.service.models.participation.entity.Participation;
import ru.practicum.service.models.participation.entity.ParticipationStatus;
import ru.practicum.service.models.user.entity.User;
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
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    public UserEventServiceImpl(EventRepository eventRepository, UserRepository userRepository,
                                CategoriesRepository categoriesRepository,
                                ParticipationRepository participationRepository, EventMapper eventMapper,
                                CommentMapper commentMapper, CommentRepository commentRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.categoriesRepository = categoriesRepository;
        this.participationRepository = participationRepository;
        this.eventMapper = eventMapper;
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
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

    @Override
    public CommentDto createComment(long userId, long eventId, CommentDto commentDto) {
        Event event = eventRepository.getEventById(eventId);
        Validation.validationEvent(event, eventId);
        if (LocalDateTime.now().plusHours(1).isAfter(event.getEventDate())) {
            throw new ForbiddenException(String
                    .format("До начала события ID = %d менее 1 часа, редактирование запрещено", eventId));
        }
        if (event.getState() != EventStatus.PUBLISHED) {
            throw new ForbiddenException(String
                    .format("Событие ID = %d не опубликовано, невозможно оставить комментарий", eventId));
        }
        Comment comment = commentMapper.toComment(commentDto);
        comment.setAuthor(userRepository.getUserById(userId));
        comment.setEvent(eventRepository.getEventById(eventId));
        comment.setCreated(DateTimeSetter.setDateTime());
        return commentMapper.toCommentDto(commentRepository.saveAndFlush(comment));
    }

    @Override
    public void deleteComment(long userId, long eventId, long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new NotFoundException("Комментарий не найден"));
        if(comment.getAuthor().getId() != userId){
            throw new ValidationException("Пользователь не может удалить чужой комментарий");
        }
        commentRepository.deleteById(commentId);
    }

    public void validateUserById(long userId) {
        if (userRepository.getUserById(userId) == null) {
            log.warn("Пользователя с ID {} не существует", userId);
            throw new FalseIdException("Пользователя с ID " + userId + " не существует");
        }
    }
}
