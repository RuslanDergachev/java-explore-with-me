package ru.practicum.service.admin.controllers.compilations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.admin.services.compilations.AdminCompilationsService;
import ru.practicum.service.entitys.compilation.model.dto.CompilationDto;
import ru.practicum.service.entitys.compilation.model.dto.NewCompilationDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RestController
@RequestMapping("/admin/compilations")
public class AdminCompilationsController {

    private final AdminCompilationsService adminCompilationsService;


    public AdminCompilationsController(AdminCompilationsService adminCompilationsService) {
        this.adminCompilationsService = adminCompilationsService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto addNewCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Создание новой подборки событий: {}", newCompilationDto);
        return adminCompilationsService.addNewCompilation(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompilation(@Positive @PathVariable long compId) {
        log.info("Удалена подборка событий {}", compId);
        adminCompilationsService.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompilation(@Positive @PathVariable long compId, @Positive @PathVariable long eventId) {
        log.info("Удалено событие {} из подборки событий {}", eventId, compId);
        adminCompilationsService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void addEventInCompilation(@Positive @PathVariable long compId, @Positive @PathVariable long eventId) {
        log.info("Добавлено событие {} в подборку событий {}", eventId, compId);
        adminCompilationsService.addEventInCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    @ResponseStatus(HttpStatus.OK)
    public void notPinnedCompilation(@Positive @PathVariable long compId) {
        log.info("Подборка событий {} снята с публикации", compId);
        adminCompilationsService.notPinnedCompilation(compId);
    }

    @PatchMapping("/{compId}/pin")
    @ResponseStatus(HttpStatus.OK)
    public void pinnedCompilation(@Positive @PathVariable long compId) {
        log.info("Подборка событий {} опубликована", compId);
        adminCompilationsService.pinnedCompilation(compId);
    }

}
