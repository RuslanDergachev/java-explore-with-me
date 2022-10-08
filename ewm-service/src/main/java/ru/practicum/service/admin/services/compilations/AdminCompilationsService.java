package ru.practicum.service.admin.services.compilations;

import ru.practicum.service.entitys.compilation.model.dto.CompilationDto;
import ru.practicum.service.entitys.compilation.model.dto.NewCompilationDto;

public interface AdminCompilationsService {

    CompilationDto addNewCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(long compId);

    void deleteEventFromCompilation(long compId, long eventId);

    void addEventInCompilation(long compId, long eventId);

    void notPinnedCompilation(long compId);

    void pinnedCompilation(long compId);
}
