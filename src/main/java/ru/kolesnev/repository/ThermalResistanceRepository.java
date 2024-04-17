package ru.kolesnev.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kolesnev.domain.ThermalProperty;
import ru.kolesnev.domain.ThermalResistance;
import ru.kolesnev.domain.ThermalResistanceId;
import ru.kolesnev.enums.SurfaceType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ThermalResistanceRepository extends JpaRepository<ThermalResistance, ThermalResistanceId> {

    @Query(value = """
            SELECT tr
            FROM ThermalResistance tr
            WHERE tr.resistanceId = :thermalId
            """)
    Optional<ThermalResistance> findByThermalId(ThermalResistanceId thermalId);

    @Query(value = """
            SELECT tr
            FROM ThermalResistance tr
            where tr.resistanceId.temperature <= :temp
            and tr.resistanceId.nominalDiameter <= :diameter
            and tr.resistanceId.surfaceType in :types
            order by tr.resistanceId.temperature desc, tr.resistanceId.nominalDiameter desc
            """)
    List<ThermalResistance> selectResMinTempMinDiameter(Short temp, Integer diameter, List<SurfaceType> types, Pageable pageable);
}
