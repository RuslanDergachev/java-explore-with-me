package ru.practicum.service.allstorage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.service.entitys.locations.entity.Locations;

public interface LocationRepository extends JpaRepository<Locations, Long> {
}
