package ru.practicum.service.models.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * The class describes a brief structure of the event entity to update.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest {
    private String annotation;
    private Long category;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Long eventId;
    private String title;
    private Boolean paid;
    private Long participantLimit;
}
