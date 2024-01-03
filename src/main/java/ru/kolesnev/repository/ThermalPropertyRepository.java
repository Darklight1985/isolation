package ru.kolesnev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kolesnev.domain.ThermalProperty;
import ru.kolesnev.domain.ThermalPropertyId;

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

    void deleteByThermalPropertyId(ThermalPropertyId id);

    @Query(value = """
               DELETE FROM ThermalProperty tp
               where tp.thermalPropertyId.isolation.id = :id
            """)
    int deleteAllByIsolation(UUID id);
}
