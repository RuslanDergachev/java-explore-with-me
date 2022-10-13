package ru.practicum.service.services.allclients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.service.repositories.CompilationRepository;
import ru.practicum.service.models.compilation.dto.CompilationDto;
import ru.practicum.service.mappers.CompilationMapper;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    public CompilationServiceImpl(CompilationRepository compilationRepository, CompilationMapper compilationMapper) {
        this.compilationRepository = compilationRepository;
        this.compilationMapper = compilationMapper;
    }

    @Override
    public CompilationDto getCompilationById(long compId) {
        return compilationMapper.toCompilationDto(compilationRepository.findCompilationById(compId));
    }

    @Override
    public List<CompilationDto> getCompilations(boolean pinned, int from, int size) {
        Pageable pageable = PageRequest.of(from, size, Sort.by("id"));
        return compilationRepository.findCompilationByPinnedIs(pinned, pageable).stream()
                .map(compilationMapper::toCompilationDto).collect(Collectors.toList());
    }
}
