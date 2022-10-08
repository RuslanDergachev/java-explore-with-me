package ru.practicum.service.entitys.eventcategory.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class EventCategoryDto {
    private Long id;
    @NotBlank
    private String name;
}
