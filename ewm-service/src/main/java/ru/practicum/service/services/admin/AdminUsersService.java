package ru.practicum.service.services.admin;

import ru.practicum.service.models.user.dto.UserDto;
import ru.practicum.service.models.user.entity.User;

import java.util.List;

/**
 * User Experience class
 */
public interface AdminUsersService {

    /**
     * Method of creating a new user
     * @param user entity user
     * @return User object new user
     */
    User addNewUser(User user);

    /**
     * Search method for all users
     * @param usersId user id
     * @param from the number of events that need to be skipped to form the current set
     * @param size number of events in the set
     * @return List<UserDto> object list of all users
     */
    List<UserDto> findAllUsers(List<Long> usersId, int from, int size);

    /**
     * Method of deleting a user
     * @param userId user id
     */
    void removeUser(long userId);

}
