package ru.practicum.service.mappers;

import ru.practicum.service.models.locations.dto.LocationDto;
import ru.practicum.service.models.locations.entity.Locations;

public class LocationMapper {

    public static LocationDto toLocationDto(Locations locations) {
        return LocationDto.builder()
                .lat(locations.getLat())
                .lon(locations.getLon())
                .build();
    }
}
