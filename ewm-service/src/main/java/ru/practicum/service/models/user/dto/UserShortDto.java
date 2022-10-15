package ru.practicum.service.models.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * The class describes the user
 */
@Data
@Builder
public class UserShortDto {
        private Long id;
        @NotBlank
        private String name;
}
