package ru.practicum.service.models.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * The class describes the user
 */
@Data
@Builder
public class UserDto {
    private Long id;
    @NotBlank
    private String name;
    @Email
    private String email;

    public UserDto(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
