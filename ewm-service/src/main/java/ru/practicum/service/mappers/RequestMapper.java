package ru.practicum.service.mappers;

import ru.practicum.service.models.participation.dto.ParticipationRequestDto;
import ru.practicum.service.models.participation.entity.Participation;

public class RequestMapper {

    public static Participation toParticipation(ParticipationRequestDto participationRequestDto) {
        return Participation.builder()
                .id(participationRequestDto.getId())
                .created(participationRequestDto.getCreated())
                .event(null)
                .requester(null)
                .status(participationRequestDto.getStatus())
                .build();
    }

    public static ParticipationRequestDto toParticipationRequestDto(Participation participation) {
        return ParticipationRequestDto.builder()
                .id(participation.getId())
                .created(participation.getCreated())
                .event(participation.getEvent().getId())
                .requester(participation.getRequester().getId())
                .status(participation.getStatus())
                .build();
    }
}
