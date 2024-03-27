package ru.kolesnev.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kolesnev.domain.HeatFlux;
import ru.kolesnev.domain.ThermalProperty;

import java.util.List;
import java.util.UUID;

public interface HeatFluxRepository extends JpaRepository<HeatFlux, UUID> {

    @Query(value = """
            SELECT hf from HeatFlux hf
            where hf.heatFluxId.nominalDiameter = :nominalDiameter
            and hf.heatFluxId.indoors = :indoors
            and hf.heatFluxId.longWork = :longWork
            and hf.heatFluxId.temperature >= :minTemperature
            and hf.heatFluxId.temperature <= :maxTemperature
            """)
    List<HeatFlux> findHeatFluxByCondition(Short minTemperature, Short maxTemperature, Integer nominalDiameter,
                                                       boolean longWork, boolean indoors);

    @Query(value = """
            SELECT hf from HeatFlux hf
            where hf.heatFluxId.nominalDiameter = :nominalDiameter
            and hf.heatFluxId.indoors = :indoors
            and hf.heatFluxId.longWork = :longWork
            and hf.heatFluxId.temperature >= :temp
            order by hf.heatFluxId.temperature
            """)
    List<HeatFlux> selectPropertyForMax(Short temp, Integer nominalDiameter, boolean longWork, boolean indoors, Pageable pageable);

    @Query(value = """
            SELECT hf from HeatFlux hf
            where hf.heatFluxId.nominalDiameter = :nominalDiameter
            and hf.heatFluxId.indoors = :indoors
            and hf.heatFluxId.longWork = :longWork
            and hf.heatFluxId.temperature <= :temp
            order by hf.heatFluxId.temperature desc 
            """)
    List<HeatFlux> selectPropertyFoMin(Short temp, Integer nominalDiameter, boolean longWork, boolean indoors, Pageable pageable);
}
