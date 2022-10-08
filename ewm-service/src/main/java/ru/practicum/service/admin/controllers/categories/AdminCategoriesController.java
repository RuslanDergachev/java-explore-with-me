package ru.practicum.service.admin.controllers.categories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.admin.services.categories.AdminCategoriesService;
import ru.practicum.service.entitys.eventcategory.model.dto.EventCategoryDto;
import ru.practicum.service.entitys.eventcategory.model.dto.EventCategoryMapper;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

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

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public EventCategoryDto addNewCategories(@Valid @RequestBody EventCategoryDto eventCategoryDto) {
        log.info("Создание новой категории: {}", eventCategoryDto.getName());

        return EventCategoryMapper.toEventCategoryDto(
                adminCategoriesService.addNewCategory(EventCategoryMapper.toEventCategory(eventCategoryDto)));
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public EventCategoryDto changeCategory(@RequestBody EventCategoryDto eventCategoryDto) {
        log.info("Изменена категория {}", eventCategoryDto);
        return adminCategoriesService.changeCategory(eventCategoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@Positive @PathVariable long catId) {
        log.info("Удалена категория {}", catId);
        adminCategoriesService.deleteCategory(catId);
    }
}
