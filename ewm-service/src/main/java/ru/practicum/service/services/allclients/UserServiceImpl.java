package ru.practicum.service.services.allclients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.service.repositories.UserRepository;
import ru.practicum.service.models.user.dto.UserDto;
import ru.practicum.service.mappers.UserMapper;
import ru.practicum.service.models.user.entity.User;
import ru.practicum.service.exception.NotFoundException;
import ru.practicum.service.exception.ValidationException;

import java.util.List;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("такого пользователя нет в списке"));
        return user;
    }

    @Transactional
    public User createUser(User user) {
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
        userRepository.save(user);
        return user;
    }

    public UserDto updateUser(long userId, User user) {
        User saveUser = userRepository.getReferenceById(userId);
        if (user.getName() != null) {
            saveUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            saveUser.setEmail(user.getEmail());
        }
        userRepository.saveAndFlush(saveUser);
        return UserMapper.toUserDto(saveUser);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void removeUser(long userId) {
        userRepository.deleteById(userId);
    }
}
