package ru.practicum.service.admin.services.compilations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.service.allstorage.CompilationRepository;
import ru.practicum.service.allstorage.EventRepository;
import ru.practicum.service.entitys.compilation.model.dto.CompilationDto;
import ru.practicum.service.entitys.compilation.model.dto.CompilationMapper;
import ru.practicum.service.entitys.compilation.model.dto.NewCompilationDto;
import ru.practicum.service.entitys.compilation.model.entity.Compilation;
import ru.practicum.service.entitys.event.model.entity.Event;
import ru.practicum.service.exception.NotFoundException;
import ru.practicum.service.exception.ValidationException;
import ru.practicum.service.validation.Validation;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminCompilationsServiceImpl implements AdminCompilationsService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;

    public AdminCompilationsServiceImpl(CompilationRepository compilationRepository, EventRepository eventRepository, CompilationMapper compilationMapper) {
        this.compilationRepository = compilationRepository;
        this.eventRepository = eventRepository;
        this.compilationMapper = compilationMapper;
    }

    @Override
    public CompilationDto addNewCompilation(NewCompilationDto newCompilationDto) {
        if (newCompilationDto == null) {
            log.warn("Подборка событий пустая");
            throw new ValidationException("Подборка событий пустая");
        }
        Compilation compilation = CompilationMapper.toCompilation(newCompilationDto);
        List<Event> events = eventRepository.findAllById(newCompilationDto.getEvents());
        compilation.setEvents(events);
        return compilationMapper.toCompilationDto(compilationRepository.saveAndFlush(compilation));
    }

    @Override
    public void deleteCompilation(long compId) {
        if (compilationRepository.findById(compId).isEmpty()) {
            log.warn("Подборка событий не найдена");
            throw new NotFoundException(String.format("Подборка ID = %d не найдена", compId));
        }
        compilationRepository.deleteById(compId);
    }

    @Override
    public void deleteEventFromCompilation(long compId, long eventId) {
        Compilation compilation = compilationRepository.findCompilationById(compId);
        if (compilation == null) {
            throw new NotFoundException(String.format("Подборка событий ID = %d не найдена", compId));
        }
        Validation.validationCompilation(compilation, compId);
        List<Event> events = compilation.getEvents().stream().filter(event -> event.getId() != eventId)
                .collect(Collectors.toList());
        compilation.setEvents(events);
        compilationRepository.saveAndFlush(compilation);
    }

    @Override
    public void addEventInCompilation(long compId, long eventId) {
        Event event = eventRepository.getEventById(eventId);
        Validation.validationEvent(event, eventId);
        Compilation compilation = compilationRepository.findCompilationById(compId);
        Validation.validationCompilation(compilation, compId);
        List<Event> events = compilation.getEvents();
        events.add(event);
        compilation.setEvents(events);
        compilationRepository.saveAndFlush(compilation);
    }

    @Override
    public void notPinnedCompilation(long compId) {
        Compilation compilation = compilationRepository.findCompilationById(compId);
        Validation.validationCompilation(compilation, compId);
        compilation.setPinned(false);
        compilationRepository.saveAndFlush(compilation);
    }

    @Override
    public void pinnedCompilation(long compId) {
        Compilation compilation = compilationRepository.findCompilationById(compId);
        Validation.validationCompilation(compilation, compId);
        compilation.setPinned(true);
        compilationRepository.saveAndFlush(compilation);
    }
}
