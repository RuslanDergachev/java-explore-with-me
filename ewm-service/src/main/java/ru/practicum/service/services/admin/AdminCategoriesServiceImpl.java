package ru.practicum.service.services.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.service.exception.ForbiddenException;
import ru.practicum.service.exception.NotFoundException;
import ru.practicum.service.exception.ValidationException;
import ru.practicum.service.mappers.EventCategoryMapper;
import ru.practicum.service.models.event.entity.Event;
import ru.practicum.service.models.eventcategory.dto.EventCategoryDto;
import ru.practicum.service.models.eventcategory.entity.EventCategory;
import ru.practicum.service.repositories.CategoriesRepository;
import ru.practicum.service.repositories.EventRepository;

import java.util.List;

@Slf4j
@Service
public class AdminCategoriesServiceImpl implements AdminCategoriesService {

    private final CategoriesRepository categoriesRepository;
    private final EventRepository eventRepository;

    public AdminCategoriesServiceImpl(CategoriesRepository categoriesRepository, EventRepository eventRepository) {
        this.categoriesRepository = categoriesRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public EventCategory addNewCategory(EventCategory eventCategory) {
        if (eventCategory.getName() == null) {
            log.warn("Отсутсвует наименование категории");
            throw new ValidationException("Отсутсвует наименование категории");
        }
        return categoriesRepository.saveAndFlush(eventCategory);
    }

    @Override
    public EventCategoryDto changeCategory(EventCategoryDto eventCategoryDto) {
        if (eventCategoryDto == null) {
            log.warn("Запрос на изменение категории пустой");
            throw new ValidationException("Запрос на изменение категории пустой");
        }
        EventCategory newEventCategory = EventCategoryMapper.toEventCategory(eventCategoryDto);
        EventCategory eventCategory = categoriesRepository.findEventCategoriesByName(eventCategoryDto.getName());
        if (eventCategory == null) {
            return EventCategoryMapper.toEventCategoryDto(categoriesRepository.saveAndFlush(newEventCategory));
        }
        log.warn("Категория {} уже существует", eventCategoryDto);
        throw new ForbiddenException(String.format("Категория " + eventCategory.getName() + " уже существует"));
    }

    @Override
    public void deleteCategory(long catId) {
        if (categoriesRepository.findById(catId).isEmpty()) {
            log.warn("Категория {} не найдена", catId);
            throw new NotFoundException(String.format("Категория ID = %d не найдена", catId));
        }
        List<Event> events = eventRepository.findEventsByCategoryId(catId);
        if (events.size() == 0) {
            categoriesRepository.deleteById(catId);
        } else {
            log.warn("Невозможно удалить используемую категорию {}", catId);
            throw new ForbiddenException(String.format("Невозможно удалить используемую категорию = %d", catId));
        }
    }
}
