package ru.kolesnev.security;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ru.kolesnev.csv.CSVBean;
import ru.kolesnev.csv.ThermalResistanceCSVBean;
import ru.kolesnev.domain.User;

@Singleton
@RequiredArgsConstructor
public class StartUp {

    @Inject
    private Instance<CSVBean> lists;

    @Transactional
    public void loadUsers(@Observes StartupEvent evt) throws Exception {
        if (!User.checkAdmin()) {
            User.add("admin", "admin", "admin");
        }

        lists.forEach(csvBean -> csvBean.run());
    }
}
