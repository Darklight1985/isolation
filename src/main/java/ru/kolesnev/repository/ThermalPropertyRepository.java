package ru.kolesnev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kolesnev.domain.ThermalProperty;

import java.util.List;
import java.util.UUID;



public interface ThermalPropertyRepository extends JpaRepository<ThermalProperty, UUID> {

    @Query(value = """
            SELECT tp
            FROM ThermalProperty tp
            WHERE tp.thermalPropertyId.isolation.id = :id
            """)
    List<ThermalProperty> findAllThermalPropertyByIsolationID(UUID id);

    @Query(value = """
            SELECT tp
            FROM ThermalProperty tp
            WHERE tp.thermalPropertyId.isolation.id in :ids
            """)
    List<ThermalProperty> findAllThermalPropertyByIsolationIDs(List<UUID> ids);
}
