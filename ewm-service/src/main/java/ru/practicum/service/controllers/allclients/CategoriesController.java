package ru.practicum.service.controllers.allclients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.services.allclients.CategoryService;
import ru.practicum.service.models.eventcategory.dto.EventCategoryDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Public API for working with categories
 */
@RestController
@RequestMapping(path = "/categories")
@Slf4j
@Validated
public class CategoriesController {

    private final CategoryService categoryService;

    public CategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Getting categories
     * @param from the number of elements that need to be skipped to form the current set
     * @param size number of items in the set
     * @return List<EventCategoryDto> object contains a list of categories
     */
    @GetMapping
    public List<EventCategoryDto> getCategories(
            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
            @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get categories from={}, size={}", from, size);
        return categoryService.getCategories(from, size);
    }

    /**
     * Getting information about a category by its ID
     * @param catId category id
     * @return EventCategoryDto object contains a description of the category
     */
    @GetMapping("/{catId}")
    public EventCategoryDto getCategoryById(@Positive @PathVariable Long catId) {
        log.info("Get category by id {}", catId);
        return categoryService.getCategoryById(catId);
    }
}
