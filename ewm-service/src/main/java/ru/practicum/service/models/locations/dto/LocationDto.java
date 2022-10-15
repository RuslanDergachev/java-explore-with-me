package ru.practicum.service.models.locations.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The class describes the structure of the location entity
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    private float lat;
    private float lon;
}
