package ru.practicum.statservice.logeventsrequests.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewsStatsDto {
    private String app;
    private String uri;
    private Long hits;
}
