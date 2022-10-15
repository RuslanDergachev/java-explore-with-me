package ru.practicum.service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.service.models.compilation.entity.Compilation;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    Page<Compilation> findCompilationByPinnedIs(boolean pinned, Pageable pageable);

    Compilation findCompilationById(long compilationId);
}
