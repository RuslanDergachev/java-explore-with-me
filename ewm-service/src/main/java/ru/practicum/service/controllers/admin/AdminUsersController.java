package ru.practicum.service.controllers.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.services.admin.AdminUsersService;
import ru.practicum.service.models.user.dto.UserDto;
import ru.practicum.service.mappers.UserMapper;

import javax.validation.Valid;
import java.util.List;

/**
 * API for working with users
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/admin/users")
public class AdminUsersController {

    AdminUsersService adminUsersService;

    @Autowired
    public AdminUsersController(AdminUsersService adminUsersService) {
        this.adminUsersService = adminUsersService;
    }

    /**
     * Adding a new user
     * @param userDto contains the data of a new user
     * @return UserDto contains the data of a new user
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto addNewUser(@Valid @RequestBody UserDto userDto) {
        log.info("Создание нового пользователя: {}", userDto);

        return UserMapper.toUserDto(adminUsersService.addNewUser(UserMapper.toUser(userDto)));
    }

    /**
     * Returns information about all users (the selection restriction parameters are taken into account),
     * or about specific ones (the specified identifiers are taken into account)
     * @param ids users id
     * @param from the number of events that need to be skipped to form the current set
     * @param size number of events in the set
     * @return List<UserDto> object contains a list of users
     */
    @GetMapping()
    public List<UserDto> get(@RequestParam(value = "ids", required = false) List<Long> ids,
                             @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
                             @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        log.info("Получен запрос списка пользователей");
        return adminUsersService.findAllUsers(ids, from, size);
    }

    /**
     * deleting a user
     * @param id user id
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeUser(@PathVariable long id) {
        log.info("Удален пользователь {}", id);
        adminUsersService.removeUser(id);
    }
}
