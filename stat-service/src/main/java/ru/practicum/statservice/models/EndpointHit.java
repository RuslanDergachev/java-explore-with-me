package ru.practicum.statservice.models;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The class describes an entity for saving statistics
 */
@Entity
@Table(name = "log_request_events", schema = "public")
@Getter
@Setter
@ToString
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "app")
    private String app; //Идентификатор сервиса для которого записывается информация
    @Column(name = "uri")
    private String uri; //URI для которого был осуществлен запрос
    private String ip; //IP-адрес пользователя, осуществившего запрос
    private LocalDateTime time = LocalDateTime.now(); //Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
}
