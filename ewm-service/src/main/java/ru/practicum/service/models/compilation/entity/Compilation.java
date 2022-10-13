package ru.practicum.service.models.compilation.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import ru.practicum.service.models.event.entity.Event;

import javax.persistence.*;
import java.util.List;

/**
 * The class describes the structure of the event collection entity
 */
@Entity
@Table(name = "compilations", schema = "public")
@Getter
@Setter
@ToString
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compilation_id")
    private Long id;
    private String title;
    @ManyToMany
    @JoinTable(
            name = "compilations_events",
            schema = "public",
            joinColumns = @JoinColumn(name = "compilations_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    @ToString.Exclude
    private List<Event> events;
    private Boolean pinned;
}
