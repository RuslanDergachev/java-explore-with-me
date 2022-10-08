package ru.practicum.service.allstorage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.service.entitys.user.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User getUserById(long id);

    Page<User> findAll(Pageable pageable);
}
