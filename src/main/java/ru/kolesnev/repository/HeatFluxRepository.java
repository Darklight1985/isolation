package ru.kolesnev.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import ru.kolesnev.domain.HeatFlux;
import ru.kolesnev.domain.HeatFluxId;
import ru.kolesnev.domain.ThermalResistance;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class HeatFluxRepository implements PanacheRepository<HeatFlux> {

    private final EntityManager entityManager;

    public HeatFluxRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Double getHeatFlux(Boolean indoors, Boolean longWork, Integer diameter, Integer temperature) {
        return (Double) entityManager.createNativeQuery("select get_heat_flux(:indoors, :longWork, :diameter, :temperature)", Double.class)
                .setParameter("indoors", indoors)
                .setParameter("longWork", longWork)
                .setParameter("diameter", diameter)
                .setParameter("temperature", temperature)
                .getSingleResult();
    }

    public boolean isExists(HeatFluxId id) {
        return count("heatFluxId", id) > 0;
    }

    public void saveAll(List<HeatFlux> list) {
        persist(list);
    }

    public Optional<HeatFlux> checkVoid() {
        return findAll().range(0, 1).firstResultOptional();
    }
}
