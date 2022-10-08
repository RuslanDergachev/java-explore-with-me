package ru.practicum.service.admin.services.categories;

import ru.practicum.service.entitys.eventcategory.model.dto.EventCategoryDto;
import ru.practicum.service.entitys.eventcategory.model.entity.EventCategory;

public interface AdminCategoriesService {

    EventCategory addNewCategory(EventCategory eventCategory);

    EventCategoryDto changeCategory(EventCategoryDto eventCategoryDto);

    void deleteCategory(long catId);
}
