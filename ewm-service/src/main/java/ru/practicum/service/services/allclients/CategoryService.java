package ru.practicum.service.services.allclients;

import ru.practicum.service.models.eventcategory.dto.EventCategoryDto;

import java.util.List;

/**
 * A class for working with event categories
 */
public interface CategoryService {

    /**
     * Method for getting a list of categories
     * @param from the number of events that need to be skipped to form the current set
     * @param size number of events in the set
     * @return List<EventCategoryDto> object list of categories
     */
    List<EventCategoryDto> getCategories(int from, int size);

    /**
     * Method of getting a category by ID
     * @param catId category id
     * @return EventCategoryDto object entity category
     */
    EventCategoryDto getCategoryById(long catId);
}
