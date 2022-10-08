package ru.practicum.service.allstorage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.service.entitys.eventcategory.model.entity.EventCategory;

public interface CategoriesRepository extends JpaRepository<EventCategory, Long> {

    Page<EventCategory> findAll(Pageable pageable);

    EventCategory findEventCategoriesByName(String name);

}
