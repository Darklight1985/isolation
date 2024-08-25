package ru.kolesnev.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import ru.kolesnev.domain.HeatFluxId;
import ru.kolesnev.domain.ThermalResistance;
import ru.kolesnev.domain.ThermalResistanceId;

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

    public boolean isExists(ThermalResistanceId id) {
        return count("resistanceId", id) > 0;
    }

    public void saveAll(List<ThermalResistance> list) {
        persist(list);
    }
}
