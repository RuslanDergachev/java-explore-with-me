package ru.practicum.service.models.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.service.models.locations.entity.Locations;

import java.time.LocalDateTime;

/**
 * The class describes the structure of the event entity. Accepted at the entrance
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private String annotation;
    private Long category;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Long eventId;
    private Locations location;
    private String title;
    private Boolean paid;
    private Long participantLimit;
    private Boolean requestModeration;
}
