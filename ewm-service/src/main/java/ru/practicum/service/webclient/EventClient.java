package ru.practicum.service.webclient;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.statservice.models.EndpointHit;
import ru.practicum.statservice.models.ViewsStatsDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * A class for working with the statistics module
 */
@Component
@AllArgsConstructor
public class EventClient {

    private final WebClient webClient;

    /**
     * Method of saving statistics
     * @param request HttpServletRequest entity
     */
    public void addViews(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
        webClient.post()
                .uri("/hit")
                .body(Mono.just(EndpointHit.builder()
                        .app("ewm-service")
                        .uri(uri)
                        .ip(ip)
                        .build()), EndpointHit.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    /**
     * Method of obtaining statistical data by criteria
     * @param start date and time not earlier than when the event should occur
     * @param end date and time no later than which the event should occur
     * @param uris list of uri
     * @param unique boolean parameter
     * @return List<ViewsStatsDto> object list views
     */
    public List<ViewsStatsDto> getViews(String start, String end, List<String> uris, Boolean unique) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stats")
                        .queryParam("start", start)
                        .queryParam("end", end)
                        .queryParam("uris", uris)
                        .queryParam("unique", unique)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ViewsStatsDto>>() {
                })
                .block();
    }
}

