package ru.practicum.service.entitys.participation.model.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import ru.practicum.service.entitys.event.model.entity.Event;
import ru.practicum.service.entitys.user.model.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "participations", schema = "public")
@Getter
@Setter
@ToString
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_id")
    private Long id;
    private LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ParticipationStatus status;
}
