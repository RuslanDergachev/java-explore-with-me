package ru.practicum.service.validation;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.service.models.compilation.entity.Compilation;
import ru.practicum.service.models.event.dto.EventDto;
import ru.practicum.service.models.event.entity.Event;
import ru.practicum.service.exception.NotFoundException;
import ru.practicum.service.exception.ValidationException;

/**
 * A class of validation methods
 */
@Slf4j
public class Validation {

    /**
     * Event validation method
     * @param event entity event
     * @param eventId event id
     */
    public static void validationEvent(Event event, long eventId) {
        if (event == null) {
            log.warn("Событие ID = {} не найдено", eventId);
            throw new NotFoundException(String.format("Событие ID = %d не найдено", eventId));
        }
    }

    /**
     * Event validation method
     * @param eventDto entity eventDto
     */
    public static void validateEventDto(EventDto eventDto) {
        if (eventDto == null) {
            log.warn("Событие пустое");
            throw new ValidationException("Событие пустое");
        }
    }

    /**
     * Selection validation method
     * @param compilation entity compilation
     * @param compId compilation id
     */
    public static void validationCompilation(Compilation compilation, long compId) {
        if (compilation == null) {
            log.warn("Подборка событий не найдена");
            throw new NotFoundException(String.format("Подборка событий ID = %d не найдена", compId));
        }
    }
}
