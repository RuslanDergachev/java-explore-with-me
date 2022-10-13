package ru.practicum.service.controllers.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.services.admin.AdminCategoriesService;
import ru.practicum.service.models.eventcategory.dto.EventCategoryDto;
import ru.practicum.service.mappers.EventCategoryMapper;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

/**
 * API for working with categories
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/admin/categories")
public class AdminCategoriesController {

    private final AdminCategoriesService adminCategoriesService;

    @Autowired
    public AdminCategoriesController(AdminCategoriesService adminCategoriesService) {
        this.adminCategoriesService = adminCategoriesService;
    }

    /**
     * Adding a new category
     * @param eventCategoryDto new category
     * @return EventCategoryDto object contains a new category
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public EventCategoryDto addNewCategories(@Valid @RequestBody EventCategoryDto eventCategoryDto) {
        log.info("Создание новой категории: {}", eventCategoryDto.getName());

        return EventCategoryMapper.toEventCategoryDto(
                adminCategoriesService.addNewCategory(EventCategoryMapper.toEventCategory(eventCategoryDto)));
    }

    /**
     * Changing the category
     * @param eventCategoryDto changed category
     * @return EventCategoryDto object changed category
     */
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public EventCategoryDto changeCategory(@RequestBody EventCategoryDto eventCategoryDto) {
        log.info("Изменена категория {}", eventCategoryDto);
        return adminCategoriesService.changeCategory(eventCategoryDto);
    }

    /**
     * Deleting a category
     * @param catId category id
     */
    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@Positive @PathVariable long catId) {
        log.info("Удалена категория {}", catId);
        adminCategoriesService.deleteCategory(catId);
    }
}
