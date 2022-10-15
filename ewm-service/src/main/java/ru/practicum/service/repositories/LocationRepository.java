package ru.practicum.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.service.models.locations.entity.Locations;

@Repository
public interface LocationRepository extends JpaRepository<Locations, Long> {
}
