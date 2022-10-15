package ru.practicum.service.mappers;

import ru.practicum.service.models.eventcategory.dto.EventCategoryDto;
import ru.practicum.service.models.eventcategory.entity.EventCategory;

public class EventCategoryMapper {

    public static EventCategoryDto toEventCategoryDto(EventCategory eventCategory) {
        return EventCategoryDto.builder()
                .id(eventCategory.getId())
                .name(eventCategory.getName())
                .build();
    }

    public static EventCategory toEventCategory(EventCategoryDto eventCategoryDto) {
        return EventCategory.builder()
                .id(eventCategoryDto.getId())
                .name(eventCategoryDto.getName())
                .build();
    }
}
