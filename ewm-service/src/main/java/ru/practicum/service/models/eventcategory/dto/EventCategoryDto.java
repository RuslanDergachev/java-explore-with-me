package ru.practicum.service.models.eventcategory.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * The class describes the structure of the category entity
 */
@Data
@Builder
public class EventCategoryDto {
    private Long id;
    @NotBlank
    private String name;
}
