package ru.practicum.service.entitys.locations.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "location", schema = "public")
@Getter
@Setter
@ToString
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Locations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;
    private float lat;
    private float lon;
}
