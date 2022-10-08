package ru.practicum.service.entitys.eventcategory.model.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "category_events", schema = "public")
@Getter
@Setter
@ToString
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name_category")
    private String name;
}
