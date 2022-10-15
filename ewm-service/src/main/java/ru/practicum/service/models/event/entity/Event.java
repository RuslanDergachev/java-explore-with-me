package ru.practicum.service.models.event.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import ru.practicum.service.models.compilation.entity.Compilation;
import ru.practicum.service.models.eventcategory.entity.EventCategory;
import ru.practicum.service.models.locations.entity.Locations;
import ru.practicum.service.models.user.entity.User;

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
