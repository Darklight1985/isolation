package ru.kolesnev.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import ru.kolesnev.csv.CSVMigration;

@ApplicationScoped
public class CVSMigrationRepository implements PanacheRepository<CSVMigration> {

    private final EntityManager em;

    public CVSMigrationRepository(EntityManager em) {
        this.em = em;
    }

    public void save(CSVMigration csvMigration) {
        persist(csvMigration);
    }

    public boolean isExists(String name) {
        return count("name", name) > 0;
    }
}