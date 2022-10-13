package ru.practicum.service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.service.models.eventcategory.entity.EventCategory;

@Repository
public interface CategoriesRepository extends JpaRepository<EventCategory, Long> {

    Page<EventCategory> findAll(Pageable pageable);

    EventCategory findEventCategoriesByName(String name);
}
