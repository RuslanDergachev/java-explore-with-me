package ru.practicum.statservice.service;


import ru.practicum.statservice.logeventsrequests.model.EndpointHit;
import ru.practicum.statservice.logeventsrequests.model.ViewsStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface LogEventsService {

    void addNewStatEvent(EndpointHit endpointHit);

    List<ViewsStatsDto> getViews(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
