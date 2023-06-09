package ru.kolesnev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolesnev.domain.Isolation;

import java.util.UUID;

public interface IsolationRepository extends JpaRepository<Isolation, UUID> {

    boolean existsByMark(String mark);
}
