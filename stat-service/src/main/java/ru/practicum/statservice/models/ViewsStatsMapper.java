package ru.practicum.statservice.models;

import org.springframework.stereotype.Component;

@Component
public class ViewsStatsMapper {

    public ViewsStatsDto toViewsStatsDto(ViewsStats viewsStats) {
        return ViewsStatsDto.builder()
                .app(viewsStats.getApp())
                .uri(viewsStats.getUri())
                .hits(viewsStats.getHits())
                .build();
    }
}
