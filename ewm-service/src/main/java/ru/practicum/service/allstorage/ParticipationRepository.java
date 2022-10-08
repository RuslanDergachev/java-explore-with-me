package ru.practicum.service.allstorage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.service.entitys.participation.model.entity.Participation;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    Participation getParticipationByEventIdAndAndRequesterId(long eventId, long requesterId);

    @Query("select e from Participation e where e.event.initiator.id = ?1 and e.event.id = ?2")
    List<Participation> getParticipationByEventId(long userId, long eventId);

    List<Participation> findAllByStatus(String status);

    @Query("select e from Participation e where e.requester.id = ?1 and e.event.initiator.id <> ?1")
    List<Participation> getParticipationByUserId(long userId);
}
