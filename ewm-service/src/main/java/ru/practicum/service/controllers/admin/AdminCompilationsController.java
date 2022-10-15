package ru.practicum.service.controllers.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.services.admin.AdminCompilationsService;
import ru.practicum.service.models.compilation.dto.CompilationDto;
import ru.practicum.service.models.compilation.dto.NewCompilationDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

/**
 * API for working with collections of events
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/admin/compilations")
public class AdminCompilationsController {

    private final AdminCompilationsService adminCompilationsService;


    public AdminCompilationsController(AdminCompilationsService adminCompilationsService) {
        this.adminCompilationsService = adminCompilationsService;
    }

    /**
     * Adding a new collection of events
     * @param newCompilationDto new compilation
     * @return CompilationDto contains a new selection of events
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto addNewCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Создание новой подборки событий: {}", newCompilationDto);
        return adminCompilationsService.addNewCompilation(newCompilationDto);
    }

    /**
     * Deleting a collection of events
     * @param compId compilation id
     */
    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompilation(@Positive @PathVariable long compId) {
        log.info("Удалена подборка событий {}", compId);
        adminCompilationsService.deleteCompilation(compId);
    }

    /**
     * Deleting an event from a collection
     * @param compId compilation id
     * @param eventId event id
     */
    @DeleteMapping("/{compId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEventFromCompilation(@Positive @PathVariable long compId, @Positive @PathVariable long eventId) {
        log.info("Удалено событие {} из подборки событий {}", eventId, compId);
        adminCompilationsService.deleteEventFromCompilation(compId, eventId);
    }

    /**
     * Adding an event to a collection
     * @param compId compilation id
     * @param eventId event id
     */
    @PatchMapping("/{compId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void addEventInCompilation(@Positive @PathVariable long compId, @Positive @PathVariable long eventId) {
        log.info("Добавлено событие {} в подборку событий {}", eventId, compId);
        adminCompilationsService.addEventInCompilation(compId, eventId);
    }

    /**
     * Removing a selection from publication
     * @param compId compilation id
     */
    @DeleteMapping("/{compId}/pin")
    @ResponseStatus(HttpStatus.OK)
    public void notPinnedCompilation(@Positive @PathVariable long compId) {
        log.info("Подборка событий {} снята с публикации", compId);
        adminCompilationsService.notPinnedCompilation(compId);
    }

    /**
     * Publishing a selection of events
     * @param compId compilation id
     */
    @PatchMapping("/{compId}/pin")
    @ResponseStatus(HttpStatus.OK)
    public void pinnedCompilation(@Positive @PathVariable long compId) {
        log.info("Подборка событий {} опубликована", compId);
        adminCompilationsService.pinnedCompilation(compId);
    }

}
