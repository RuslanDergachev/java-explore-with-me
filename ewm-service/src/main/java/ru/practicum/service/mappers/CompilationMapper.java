package ru.practicum.service.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.service.models.compilation.dto.CompilationDto;
import ru.practicum.service.models.compilation.dto.NewCompilationDto;
import ru.practicum.service.models.compilation.entity.Compilation;

@Component
public class CompilationMapper {

    EventMapper eventMapper;

    public CompilationMapper(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    public CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .events(eventMapper.toListEventsDto(compilation.getEvents()))
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .build();
    }

    public static Compilation toCompilation(NewCompilationDto newCompilationDto) {
        return Compilation.builder()
                .id(null)
                .events(null)
                .pinned(newCompilationDto.getPinned())
                .title(newCompilationDto.getTitle())
                .build();
    }
}
