package ru.practicum.service.admin.services.users;

import ru.practicum.service.entitys.user.model.dto.UserDto;
import ru.practicum.service.entitys.user.model.entity.User;

import java.util.List;

public interface AdminUsersService {

    User addNewUser(User user);

    List<UserDto> findAllUsers(List<Long> usersId, int from, int size);

    void removeUser(long userId);

}
