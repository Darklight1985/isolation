package ru.kolesnev.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import ru.kolesnev.domain.Isolation;
import ru.kolesnev.domain.ThermalProperty;
import ru.kolesnev.domain.ThermalPropertyId;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ThermalPropertyRepository implements PanacheRepository<ThermalProperty> {


    private final EntityManager entityManager;

    public ThermalPropertyRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ThermalProperty> findAllThermalPropertyByIsolationID(UUID id) {
        return list("thermalPropertyId.isolation.id", id);
    }

    public List<ThermalProperty> findAllThermalPropertyByIsolationIDs(List<UUID> ids) {
        return list("thermalPropertyId.isolation.id in ?1", ids);
    }

    @Transactional
    public void deleteByThermalPropertyId(ThermalPropertyId id) {
        delete("thermalPropertyId", id);
    }

    public void deleteAllByIsolation(Isolation isolation) {
        delete("thermalPropertyId.isolation", isolation);
    }

    public Double getConductivity(UUID isolation, Integer temperature) {
        return (Double) entityManager.createNativeQuery("select get_conductivity(:isolation, :temperature)", Double.class)
                .setParameter("isolation", isolation)
                .setParameter("temperature", temperature)
                .getSingleResult();
    }

    public void save(ThermalProperty thermalProperty) {
        persist(thermalProperty);
    }
}
