package ru.practicum.service.services.allclients;

import org.springframework.stereotype.Service;
import ru.practicum.service.models.user.dto.UserDto;
import ru.practicum.service.models.user.entity.User;

import java.util.List;

/**
 * A class for working with users
 */
public interface UserService {

    /**
     * Method of searching for a user by ID
     * @param userId user id
     * @return User object entity user
     */
    User getUser(long userId);

    /**
     * Method of creating a new user
     * @param user entity user
     * @return User object new entity user
     */
    User createUser(User user);

    /**
     * User update method
     * @param userId user id
     * @param user entity user
     * @return UserDto object updates user
     */
    UserDto updateUser(long userId, User user);

    /**
     * Method for getting a list of users
     * @return List<User> object list of users
     */
    List<User> getUsers();

    /**
     * User deletion method
     * @param userId user id
     */
    void removeUser(long userId);
}
