package ru.practicum.service.allusers.services.categories.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.service.allstorage.CategoriesRepository;
import ru.practicum.service.entitys.eventcategory.model.dto.EventCategoryDto;
import ru.practicum.service.entitys.eventcategory.model.dto.EventCategoryMapper;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoriesRepository categoriesRepository;

    public CategoryServiceImpl(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public List<EventCategoryDto> getCategories(int from, int size) {
        Pageable pageable = PageRequest.of(from, size, Sort.by("id"));
        return categoriesRepository.findAll(pageable).stream()
                .map(EventCategoryMapper::toEventCategoryDto).collect(Collectors.toList());
    }

    @Override
    public EventCategoryDto getCategoryById(long catId) {
        return EventCategoryMapper.toEventCategoryDto(categoriesRepository.getReferenceById(catId));
    }
}
