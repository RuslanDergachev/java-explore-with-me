package ru.practicum.service.allstorage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.service.entitys.compilation.model.entity.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    Page<Compilation> findCompilationByPinnedIs(boolean pinned, Pageable pageable);

    Compilation findCompilationById(long compilationId);
}
