package ru.practicum.service.services.allclients;

import ru.practicum.service.models.compilation.dto.CompilationDto;

import java.util.List;

/**
 * A class for working with collections of events
 */
public interface CompilationService {

    /**
     * Method for getting a selection of events by ID
     * @param compId compilation id
     * @return CompilationDto object entity compilation
     */
    CompilationDto getCompilationById(long compId);

    /**
     * Method for getting a list of event collections
     * @param pinned search only pinned/not pinned collections
     * @param from the number of events that need to be skipped to form the current set
     * @param size number of events in the set
     * @return List<CompilationDto> object list of compilations
     */
    List<CompilationDto> getCompilations(boolean pinned, int from, int size);
}
