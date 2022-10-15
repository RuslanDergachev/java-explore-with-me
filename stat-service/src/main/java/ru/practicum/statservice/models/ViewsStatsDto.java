package ru.practicum.statservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class describes an entity for returning statistics
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewsStatsDto {
    private String app;
    private String uri;
    private Long hits;
}
