package ru.kolesnev.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.kolesnev.domain.Isolation;
import ru.kolesnev.domain.ThermalProperty;
import ru.kolesnev.domain.ThermalPropertyId;

import java.util.List;
import java.util.Optional;
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

    @Modifying
    @Query(value = """
               delete from ThermalProperty tp
               where tp.thermalPropertyId.isolation = :isolation
            """)
    void deleteAllByIsolation(Isolation isolation);


    @Query(value = """
            select tp 
            from ThermalProperty tp
            where tp.thermalPropertyId.temperature >= :temp
            and tp.thermalPropertyId.isolation.id = :isolationId
            order by tp.thermalPropertyId.temperature
            """)
    List<ThermalProperty> selectPropertyForMax(Short temp, UUID isolationId, Pageable pageable);

    @Query(value = """
            select tp 
            from ThermalProperty tp
            where tp.thermalPropertyId.temperature <= :temp
            and tp.thermalPropertyId.isolation.id = :isolationId
            order by tp.thermalPropertyId.temperature desc 
            """)
    List<ThermalProperty> selectPropertyForMin(Short temp, UUID isolationId, Pageable pageable);
}
