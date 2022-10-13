package ru.practicum.service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.service.models.event.entity.Event;
import ru.practicum.service.models.event.entity.EventStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Event getEventById(Long eventId);

    @Query("select i from Event i where i.id =?1 " +
            "and i.state = 'PUBLISHED'")
    Event getEventByIdBOrderByState(long id);

    @Query("select i from Event i where (upper(i.annotation) like upper(concat('%', ?1, '%')) " +
            "or upper(i.description) like upper(concat('%', ?1, '%'))) " +
            "and i.category.id IN ?2 " +
            "and i.paid =?3 " +
            "and i.confirmedRequests < i.participantLimit " +
            "and i.eventDate between ?4 and ?5 " +
            "and i.state = 'PUBLISHED'")
    Page<Event> searchEvents(String text, List<Long> ids, Boolean paid, LocalDateTime rangeStart,
                             LocalDateTime rangeEnd, Pageable pageable);

    @Query("select i from Event i where (upper(i.annotation) like upper(concat('%', ?1, '%')) " +
            "or upper(i.description) like upper(concat('%', ?1, '%'))) " +
            "and i.category.id IN ?2 " +
            "and i.paid =?3 " +
            "and i.confirmedRequests < i.participantLimit " +
            "and i.eventDate > ?4 " +
            "and i.state = 'PUBLISHED'")
    Page<Event> searchEventsWithoutDate(String text, List<Long> ids, Boolean paid, LocalDateTime rangeStart,
                                        Pageable pageable);

    @Query("select i from Event i where i.initiator.id = ?1")
    Page<Event> findEventByInitiator(long initiator, Pageable pageable);

    @Query("select i from Event i where i.id = ?1 and i.initiator.id = ?2")
    Event findEventByIdAndUserId(long eventId, long userId);

    @Query("select i from Event i where i.initiator.id in ?1 " +
            "and i.state in ?2 " +
            "and i.category.id in ?3 " +
            "and i.eventDate between ?4 and ?5")
    Page<Event> getEventsForAdmin(List<Long> users, List<EventStatus> states, List<Long> categories,
                                  LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);

    @Query("select i from Event i where i.initiator.id in ?1 " +
            "and i.state in ?2 " +
            "and i.category.id in ?3 " +
            "and i.eventDate > ?4")
    Page<Event> getEventsForAdminWithoutDate(List<Long> users, List<EventStatus> states, List<Long> categories,
                                             LocalDateTime rangeStart, Pageable pageable);

    @Query("select i from Event i where i.category.id = ?1")
    List<Event> findEventsByCategoryId(long categoryId);
}
