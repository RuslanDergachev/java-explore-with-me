package ru.practicum.service.admin.services.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.service.allstorage.UserRepository;
import ru.practicum.service.entitys.user.model.dto.UserDto;
import ru.practicum.service.entitys.user.model.dto.UserMapper;
import ru.practicum.service.entitys.user.model.entity.User;
import ru.practicum.service.exception.NotFoundException;
import ru.practicum.service.exception.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminUsersServiceImpl implements AdminUsersService {

    private final UserRepository userRepository;

    public AdminUsersServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User addNewUser(User user) {
        if (user.getName() == null) {
            log.warn("Имя пользователя отсутствует");
            throw new ValidationException("Нет имени пользователя");
        }
        if (user.getEmail() == null) {
            log.warn("У пользователя отсутствует email");
            throw new ValidationException("Нет адреса почты пользователя");
        }
        if (user.getId() == 0) {
            user.setId(null);
        }
        return userRepository.saveAndFlush(user);
    }

    @Override
    public List<UserDto> findAllUsers(List<Long> usersId, int from, int size) {
        Pageable pageable = PageRequest.of(from, size, Sort.by("id").descending());
        List<User> users = userRepository.findAll(pageable).getContent();
        if (usersId != null && !users.isEmpty()) {
            List<UserDto> usersById = users.stream()
                    .filter(user -> usersId.contains(user.getId())).map(UserMapper::toUserDto)
                    .collect(Collectors.toList());
            if (usersById.isEmpty()) {
                throw new NotFoundException("Пользователи с указанными ID не найдены");
            }
            return usersById;
        }
        return users.stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @Transactional
    public void removeUser(long userId) {
        userRepository.deleteById(userId);
    }
}
