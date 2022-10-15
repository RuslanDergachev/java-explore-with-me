package ru.practicum.service.services.registeredclients;

import ru.practicum.service.models.comments.dto.CommentDto;
import ru.practicum.service.models.event.dto.EventDto;
import ru.practicum.service.models.event.dto.EventFullDto;
import ru.practicum.service.models.event.dto.EventShortDto;
import ru.practicum.service.models.participation.dto.ParticipationRequestDto;

import java.util.List;

/**
 * A class for working with events for registered users
 */
public interface UserEventService {

    /**
     * Method of creating a new event
     * @param userId user id
     * @param eventDto entity event
     * @return EventFullDto object full entity event
     */
    EventFullDto addNewEvent(long userId, EventDto eventDto);

    /**
     * Event update method
     * @param userId user id
     * @param eventDto entity event
     * @return EventFullDto object full entity event
     */
    EventFullDto updateEvent(long userId, EventDto eventDto);

    /**
     * Method for creating a request to participate in an event
     * @param userId user id
     * @param eventId event id
     * @return ParticipationRequestDto object entity request
     */
    ParticipationRequestDto createRequest(long userId, long eventId);

    /**
     * Event closing method
     * @param userId user id
     * @param eventId event id
     * @return EventFullDto object full entity event
     */
    EventFullDto cancelledEvent(long userId, long eventId);

    /**
     * Method of receiving events by user ID
     * @param userId user id
     * @param from the number of events that need to be skipped to form the current set
     * @param size number of events in the set
     * @return List<EventShortDto> object list of events
     */
    List<EventShortDto> getEventsByUserId(long userId, int from, int size);

    /**
     * Method for getting an event by event and user ID
     * @param userId user id
     * @param eventId user id
     * @return EventFullDto object full entity event
     */
    EventFullDto getEventsByIdAndUserId(long userId, long eventId);

    /**
     * Method for receiving requests to participate in events by user ID
     * @param userId user id
     * @param eventId event id
     * @return List<ParticipationRequestDto> object list requests
     */
    List<ParticipationRequestDto> getRequestsByUser(long userId, long eventId);

    /**
     * The method of confirming the request to participate in the event
     * @param userId user id
     * @param eventId event id
     * @param reqId request id
     * @return ParticipationRequestDto object confirmed request
     */
    ParticipationRequestDto confirmedRequest(long userId, long eventId, long reqId);

    /**
     * The method of rejecting the request to participate in the event
     * @param userId user id
     * @param eventId event id
     * @param reqId request id
     * @return ParticipationRequestDto object reject request
     */
    ParticipationRequestDto rejectRequest(long userId, long eventId, long reqId);

    /**
     * Method of obtaining information about the current user's requests to participate in other people's events
     * @param userId user id
     * @return List<ParticipationRequestDto> object
     */
    List<ParticipationRequestDto> getOtherRequestsByUser(long userId);

    /**
     * Method of canceling your request to participate in the event
     * @param userId user id
     * @param requestId request id
     * @return ParticipationRequestDto object cancelled request
     */
    ParticipationRequestDto cancelRequest(long userId, long requestId);

    /**
     * The method creates a comment on the event
     * @param userId user id
     * @param eventId event id
     * @param comment the text of the comment to the event
     * @return CommentDto object comment
     */
    CommentDto createComment(long userId, long eventId, CommentDto comment);

    /**
     * The method removes the comment to the event
     * @param userId user id
     * @param eventId event id
     * @param commentId comment id
     */
    void deleteComment(long userId, long eventId, long commentId);
}
