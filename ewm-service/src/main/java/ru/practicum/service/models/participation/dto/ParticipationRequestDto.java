package ru.practicum.service.models.participation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.service.models.participation.entity.ParticipationStatus;

import java.time.LocalDateTime;

/**
 * The class describes the structure of the participation entity
 */
@Data
@Builder
public class ParticipationRequestDto {
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    private Long event;
    private Long requester;
    private ParticipationStatus status;
}
