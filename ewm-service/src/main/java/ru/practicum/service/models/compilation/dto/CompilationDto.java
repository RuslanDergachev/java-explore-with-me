package ru.practicum.service.models.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.service.models.event.dto.EventShortDto;

import java.util.List;

/**
 * The class describes the returned entity structure of a collection of events
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    private Long id;
    private String title;
    private List<EventShortDto> events;
    private Boolean pinned;
}
