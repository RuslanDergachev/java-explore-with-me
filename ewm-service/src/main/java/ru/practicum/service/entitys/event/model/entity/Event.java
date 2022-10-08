package ru.practicum.service.entitys.event.model.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import ru.practicum.service.entitys.compilation.model.entity.Compilation;
import ru.practicum.service.entitys.eventcategory.model.entity.EventCategory;
import ru.practicum.service.entitys.locations.entity.Locations;
import ru.practicum.service.entitys.user.model.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "events", schema = "public")
@Getter
@Setter
@ToString
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {
    private String annotation;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category")
    private EventCategory category;
    private Long confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    private LocalDateTime eventDate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "initiator_id")
    private User initiator;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Locations location;
    private Boolean paid;
    private Long participantLimit;
    private LocalDateTime publishedOn;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private EventStatus state;
    private String title;
    private Long views;
    @ManyToMany(mappedBy = "events")
    @ToString.Exclude
    Set<Compilation> compilations;
}
