package ru.practicum.service.validation;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.service.entitys.compilation.model.entity.Compilation;
import ru.practicum.service.entitys.event.model.dto.EventDto;
import ru.practicum.service.entitys.event.model.entity.Event;
import ru.practicum.service.exception.NotFoundException;
import ru.practicum.service.exception.ValidationException;

@Slf4j
public class Validation {

    public static void validationEvent(Event event, long eventId) {
        if (event == null) {
            log.warn("Событие ID = {} не найдено", eventId);
            throw new NotFoundException(String.format("Событие ID = %d не найдено", eventId));
        }
    }

    public static void validateEventDto(EventDto eventDto) {
        if (eventDto == null) {
            log.warn("Событие пустое");
            throw new ValidationException("Событие пустое");
        }
    }

    public static void validationCompilation(Compilation compilation, long compId) {
        if (compilation == null) {
            log.warn("Подборка событий не найдена");
            throw new NotFoundException(String.format("Подборка событий ID = %d не найдена", compId));
        }
    }
}
