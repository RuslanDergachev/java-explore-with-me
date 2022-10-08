package ru.practicum.service.allusers.services.user.service;

import org.springframework.stereotype.Service;
import ru.practicum.service.entitys.user.model.dto.UserDto;
import ru.practicum.service.entitys.user.model.entity.User;

import java.util.List;

@Service
public interface UserService {

    User getUser(long userId);

    User createUser(User user);

    UserDto updateUser(long userId, User user);

    List<User> getUsers();

    void removeUser(long userId);
}
