package ru.practicum.service.allusers.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.allusers.services.categories.service.CategoryService;
import ru.practicum.service.entitys.eventcategory.model.dto.EventCategoryDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@Slf4j
@Validated
public class CategoriesController {

    private final CategoryService categoryService;

    public CategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<EventCategoryDto> getCategories(
            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
            @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get categories from={}, size={}", from, size);
        return categoryService.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public EventCategoryDto getCategoryById(@Positive @PathVariable Long catId) {
        log.info("Get category by id {}", catId);
        return categoryService.getCategoryById(catId);
    }
}
