package ru.practicum.service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.service.models.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getUserById(long id);

    Page<User> findAll(Pageable pageable);
}
