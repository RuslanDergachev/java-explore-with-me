package ru.practicum.service.admin.controllers.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.admin.services.users.AdminUsersService;
import ru.practicum.service.entitys.user.model.dto.UserDto;
import ru.practicum.service.entitys.user.model.dto.UserMapper;

import javax.validation.Valid;
import java.util.List;

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

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto addNewUser(@Valid @RequestBody UserDto userDto) {
        log.info("Создание нового пользователя: {}", userDto);

        return UserMapper.toUserDto(adminUsersService.addNewUser(UserMapper.toUser(userDto)));
    }

    @GetMapping()
    public List<UserDto> get(@RequestParam(value = "ids", required = false) List<Long> ids,
                             @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
                             @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        log.info("Получен запрос списка пользователей");
        return adminUsersService.findAllUsers(ids, from, size);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeUser(@PathVariable long id) {
        log.info("Удален пользователь {}", id);
        adminUsersService.removeUser(id);
    }
}
