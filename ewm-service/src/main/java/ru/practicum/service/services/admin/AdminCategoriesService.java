package ru.practicum.service.services.admin;

import ru.practicum.service.models.eventcategory.dto.EventCategoryDto;
import ru.practicum.service.models.eventcategory.entity.EventCategory;

/**
 * A class for working with event categories
 */
public interface AdminCategoriesService {

    /**
     * Method for creating a new event category
     * @param eventCategory entity category
     * @return EventCategory object entity new category
     */
    EventCategory addNewCategory(EventCategory eventCategory);

    /**
     * The method of changing the category
     * @param eventCategoryDto entity category
     * @return EventCategoryDto object entity category
     */
    EventCategoryDto changeCategory(EventCategoryDto eventCategoryDto);

    /**
     * Category deletion method
     * @param catId category id
     */
    void deleteCategory(long catId);
}
