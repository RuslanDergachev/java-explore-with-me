package ru.practicum.service.entitys.locations.dto;

import ru.practicum.service.entitys.locations.entity.Locations;

public class LocationMapper {

    public static LocationDto toLocationDto(Locations locations) {
        return LocationDto.builder()
                .lat(locations.getLat())
                .lon(locations.getLon())
                .build();
    }
}
