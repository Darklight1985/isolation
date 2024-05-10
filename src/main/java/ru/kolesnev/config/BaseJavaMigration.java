package ru.kolesnev.config;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import ru.kolesnev.config.domain.JavaMigration;
import ru.kolesnev.config.domain.JavaMigrationRepository;

import java.time.LocalDateTime;

@Singleton
@Slf4j
public abstract class BaseJavaMigration {

    @Inject
    private JavaMigrationRepository javaMigrationRepository;

    private final String className = getClass().getSimpleName();

    @Transactional
    public void afterSingletonsInstantiated(@Observes StartupEvent event) {
        if (javaMigrationRepository.findByName(getClass().getSimpleName()).isEmpty()) {
            log.info("Migration " + className + " started");
            migrate();
            javaMigrationRepository.save(new JavaMigration(className, LocalDateTime.now()));
            log.info("Migration + " + className + " successfully validated");
        }
    }
    protected abstract void migrate();
}
