package ru.practicum.statservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.statservice.models.EndpointHit;
import ru.practicum.statservice.models.ViewsStatsMapper;
import ru.practicum.statservice.repository.LogRequestEventRepository;
import ru.practicum.statservice.models.ViewsStatsDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogEventsServiceImpl implements LogEventsService {

    private final LogRequestEventRepository logRequestEventRepository;
    private final ViewsStatsMapper viewsStatsMapper;

    @Override
    @Transactional
    public void addNewStatEvent(EndpointHit endpointHit) {
        logRequestEventRepository.save(endpointHit);
    }

    @Override
    public List<ViewsStatsDto> getViews(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (unique) {
            return logRequestEventRepository.getViews(start, end, uris).stream().map(viewsStatsMapper::toViewsStatsDto)
                    .collect(Collectors.toList());
        } else {
            return logRequestEventRepository.getUniqueViews(start, end, uris).stream()
                    .map(viewsStatsMapper::toViewsStatsDto).collect(Collectors.toList());
        }
    }
}
