package ru.practicum.statservice.service;


import ru.practicum.statservice.models.EndpointHit;
import ru.practicum.statservice.models.ViewsStatsDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The interface contains methods for saving and retrieving statistics
 */
public interface LogEventsService {

    /**
     * Method of saving statistics
     * @param endpointHit entity statistic
     */
    void addNewStatEvent(EndpointHit endpointHit);

    /**
     * Method for getting statistics on parameters
     * @param start date and time not earlier than when the event should occur
     * @param end date and time no later than which the event should occur
     * @param uris list of uris
     * @param unique boolean parameter
     * @return List<ViewsStatsDto> object
     */
    List<ViewsStatsDto> getViews(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
