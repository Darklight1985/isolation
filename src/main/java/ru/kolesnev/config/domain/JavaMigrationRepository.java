package ru.kolesnev.config.domain;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

import java.util.Optional;

@ApplicationScoped
public class JavaMigrationRepository implements PanacheRepository<JavaMigration> {

    private final EntityManager entityManager;

    public JavaMigrationRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<JavaMigration> findByName(String simpleName) {
        return find("name", simpleName).firstResultOptional();
    }

    public void save(JavaMigration javaMigration) {
        persist(javaMigration);
    }
}
