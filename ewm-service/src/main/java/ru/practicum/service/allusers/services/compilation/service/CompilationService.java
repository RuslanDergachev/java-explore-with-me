package ru.practicum.service.allusers.services.compilation.service;

import ru.practicum.service.entitys.compilation.model.dto.CompilationDto;

import java.util.List;

public interface CompilationService {

    CompilationDto getCompilationById(long compId);

    List<CompilationDto> getCompilations(boolean pinned, int from, int size);
}
