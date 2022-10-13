package ru.practicum.service.models.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.practicum.service.models.eventcategory.entity.EventCategory;
import ru.practicum.service.models.user.dto.UserShortDto;

import java.time.LocalDateTime;

/**
 * The class describes a brief structure of the event entity.
 */
@Data
@Builder
public class EventShortDto {

    private Long id;
    private String annotation;
    private EventCategory category;
    private Long confirmedRequests;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Long views;
}