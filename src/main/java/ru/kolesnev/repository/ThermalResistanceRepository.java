package ru.kolesnev.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import ru.kolesnev.domain.ThermalResistance;

import java.util.List;

@ApplicationScoped
public class ThermalResistanceRepository implements PanacheRepository<ThermalResistance> {

    private final EntityManager entityManager;

    public ThermalResistanceRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public Double getThermalResistance(Short temperature, Integer diameter, String surfaceType) {
        return (Double) entityManager.createNativeQuery("select get_thermal_resistance(:temperature, :diameter, :surfaceType)", Double.class)
                .setParameter("surfaceType", surfaceType)
                .setParameter("diameter", diameter)
                .setParameter("temperature", temperature)
                .getSingleResult();
    }
//    @Query(value = """
//            SELECT tr
//            FROM ThermalResistance tr
//            WHERE tr.resistanceId = :thermalId
//            """)
//    Optional<ThermalResistance> findByThermalId(ThermalResistanceId thermalId);
//
//    @Query(value = """
//            SELECT tr
//            FROM ThermalResistance tr
//            where tr.resistanceId.temperature <= :temp
//            and tr.resistanceId.nominalDiameter <= :diameter
//            and tr.resistanceId.surfaceType in :types
//            order by tr.resistanceId.temperature desc, tr.resistanceId.nominalDiameter desc
//            """)
//    List<ThermalResistance> selectResMinTempMinDiameter(Short temp, Integer diameter, List<SurfaceType> types, Pageable pageable);

    public void saveAll(List<ThermalResistance> list) {
        persist(list);
    }
}
