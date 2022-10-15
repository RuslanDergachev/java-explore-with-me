package ru.practicum.service.services.admin;

import ru.practicum.service.models.compilation.dto.CompilationDto;
import ru.practicum.service.models.compilation.dto.NewCompilationDto;

/**
 * A class for working with collections of events
 */
public interface AdminCompilationsService {

    /**
     * Method for creating a new collection of events
     * @param newCompilationDto entity compilation
     * @return CompilationDto object new compilation
     */
    CompilationDto addNewCompilation(NewCompilationDto newCompilationDto);

    /**
     * Method of deleting a collection of events
     * @param compId compilation id
     */
    void deleteCompilation(long compId);

    /**
     * Method for deleting an event from a collection
     * @param compId compilation id
     * @param eventId event id
     */
    void deleteEventFromCompilation(long compId, long eventId);

    /**
     * Method of adding an event to a collection
     * @param compId compilation id
     * @param eventId event id
     */
    void addEventInCompilation(long compId, long eventId);

    /**
     * Unpin a selection on the main page
     * @param compId compilation id
     */
    void notPinnedCompilation(long compId);

    /**
     * Pin a selection to the main page
     * @param compId compilation id
     */
    void pinnedCompilation(long compId);
}
