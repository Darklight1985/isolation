package ru.kolesnev.security;

import io.quarkus.arc.All;
import io.quarkus.arc.InstanceHandle;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ru.kolesnev.csv.CSVBean;
import ru.kolesnev.csv.CSVBeanLoader;
import ru.kolesnev.csv.CSVMigration;
import ru.kolesnev.domain.User;
import ru.kolesnev.repository.CVSMigrationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Singleton
@RequiredArgsConstructor
public class StartUp {

    @Inject
    @All
    private List<CSVBeanLoader> lists;

    private final CVSMigrationRepository cvsMigrationRepository;

    @Transactional
    public void loadUsers(@Observes StartupEvent evt) throws Exception {
        if (!User.checkAdmin()) {
            User.add("admin", "admin", "admin");
        }

        lists.forEach(csvBean -> {
            String name = csvBean.getFileName();
            if (!cvsMigrationRepository.isExists(name)) {
                csvBean.run();
                cvsMigrationRepository.save(new CSVMigration(name, LocalDateTime.now()));
            }
        });
    }
}
