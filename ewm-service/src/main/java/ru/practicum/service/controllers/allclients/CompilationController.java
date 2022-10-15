package ru.practicum.service.controllers.allclients;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.services.allclients.CompilationService;
import ru.practicum.service.models.compilation.dto.CompilationDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * API for getting collections of events
 */
@RestController
@RequestMapping(path = "/compilations")
@Slf4j
@Validated
public class CompilationController {

    private final CompilationService compilationService;

    public CompilationController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    /**
     * API returns a list of event collections
     * @param pinned search only pinned/not pinned collections
     * @param from the number of elements that need to be skipped to form the current set
     * @param size number of items in the set
     * @return List<CompilationDto> object contains a list of collections of events
     */
    @GetMapping
    public List<CompilationDto> getCompilations(
            @NonNull @RequestParam Boolean pinned,
            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
            @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get compilations with pinned {}, from={}, size={}", pinned, from, size);
        return compilationService.getCompilations(pinned, from, size);
    }

    /**
     * API returns a selection of events by ID
     * @param compId id of the event collection
     * @return CompilationDto object contains a selection of events
     */
    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@Positive @PathVariable Long compId) {
        log.info("Get compilation by id {}", compId);
        return compilationService.getCompilationById(compId);
    }
}
