package ru.kolesnev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolesnev.domain.HeatFlux;
import ru.kolesnev.domain.Isolation;

import java.util.UUID;

public interface HeatFluxRepository extends JpaRepository<HeatFlux, UUID> {
}
