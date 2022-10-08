package ru.practicum.service.client;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.statservice.logeventsrequests.model.EndpointHit;
import ru.practicum.statservice.logeventsrequests.model.ViewsStatsDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@AllArgsConstructor
public class EventClient {

    private final WebClient webClient;

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

