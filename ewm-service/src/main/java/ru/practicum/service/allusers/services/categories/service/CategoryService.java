package ru.practicum.service.allusers.services.categories.service;

import ru.practicum.service.entitys.eventcategory.model.dto.EventCategoryDto;

import java.util.List;

public interface CategoryService {

    List<EventCategoryDto> getCategories(int from, int size);

    EventCategoryDto getCategoryById(long catId);
}
