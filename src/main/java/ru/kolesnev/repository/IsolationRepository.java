package ru.kolesnev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kolesnev.domain.Isolation;
import ru.kolesnev.domain.ThermalProperty;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IsolationRepository extends JpaRepository<Isolation, UUID> {

    boolean existsByMark(String mark);

    @Query(value = """
            select iso from Isolation iso
            where iso.mark = :mark
            and iso.id <> :isolationId
            """)
    Optional<Isolation> existsByMark(String mark, UUID isolationId);
}
